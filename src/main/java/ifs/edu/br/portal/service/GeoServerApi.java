package ifs.edu.br.portal.service;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.sql.DataSource;

@Service
public class GeoServerApi {

    private final WebClient webClient;
    private final String baseUrl;

    private final PGSimpleDataSource dataSource;

    public GeoServerApi(
            @Value("${geoserver.baseurl}") String baseUrl,
            @Value("${geoserver.username}") String username,
            @Value("${geoserver.password}") String password,
            DataSource dataSource) {
        this.baseUrl = baseUrl;
        this.dataSource = (PGSimpleDataSource) dataSource;
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeaders(headers -> headers.setBasicAuth(username, password))
                .build();
    }

    public Mono<String> createWorkspace(String workspaceName) {
        String payload = String.format("{\"workspace\": {\"name\": \"%s\"}}", workspaceName);

        return webClient.post()
                .uri("/rest/workspaces")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> createPostgisDatastore(String workspaceName, String datastoreName) {
        String payload = String.format(
                "{\"dataStore\": {\"name\": \"%s\", \"connectionParameters\": {\"host\": \"%s\", \"port\": \"%d\", \"database\": \"%s\", \"user\": \"%s\", \"passwd\": \"%s\", \"dbtype\": \"postgis\"}}}",
                datastoreName, dataSource.getServerNames()[0], dataSource.getPortNumbers()[0], dataSource.getDatabaseName(), dataSource.getUser(), dataSource.getPassword());

        return webClient.post()
                .uri(String.format("/rest/workspaces/%s/datastores", workspaceName))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> createLayer(String workspaceName, String datastoreName, String layerName, String nativeName) {
        String payload = String.format("""
                {
                "name": "%s",
                "nativeName": "%s",
                "namespace": {
                    "name": "%s"
                },
                "title": "%s",
                "srs": "EPSG:4326",
                "cqlFilter": "INCLUDE",
                "maxFeatures": 100,
                "numDecimals": 6,
                "responseSRS": {
                    "string": [
                        4326
                    ]
                },
                "overridingServiceSRS": true,
                "skipNumberMatched": true,
                "circularArcPresent": true,
                "linearizationTolerance": 10,
                }
                """, datastoreName, nativeName, layerName, layerName);

        return webClient.post()
                .uri(String.format("/rest/workspaces/%s/datastores/%s/featuretypes", workspaceName, datastoreName))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> baseUrl + "/" + workspaceName + "/ows?service=WFS&version=1.3.0&request=GetFeature&typeName=" + workspaceName + "%3" + layerName + "&maxFeatures=100&outputFormat=application%2Fjson");

        //String.format("%s/%s/ows?service=WFS&version=1.3.0&request=GetFeature&typeName=%s:%s&outputFormat=application/json", baseUrl, workspaceName, workspaceName, layerName));

    }

    public Mono<String> listLayers(String workspaceName) {
        return webClient.get()
                .uri(String.format("/rest/workspaces/%s/layers", workspaceName))
                .retrieve()
                .bodyToMono(String.class);
    }
}