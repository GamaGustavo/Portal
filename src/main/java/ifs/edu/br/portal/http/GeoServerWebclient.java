package ifs.edu.br.portal.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.logging.Logger;

@Component
public class GeoServerWebclient {
    private final RestClient restClient;
    @Value("${geoserver.workspace}")
    private String workspace;
    @Value("${geoserver.datastore}")
    private String datastore;

    public GeoServerWebclient(@Value("${geoserver.baseurl}") String baseUrl,
                              @Value("${geoserver.username}") String username,
                              @Value("${geoserver.password}") String password) {
        restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .defaultHeaders(httpHeaders -> httpHeaders.setBasicAuth(username, password))
                .build();
    }

    public boolean publicarShape(String titulo, String nativeName) {
        String payload = String.format("""
                {
                  "featureType" : {
                    "name" : "%s",
                    "nativeName" : "%s",
                    "title" : "%s",
                    "srs" : "EPSG:4326"
                  }
                }
                """, nativeName, nativeName, titulo);
        var uri = "/rest/workspaces/{workspace}/datastores/{datastore}/featuretypes";
        var response = restClient.post().uri(uri, workspace, datastore).body(payload).retrieve().toBodilessEntity();

        return response.getStatusCode().is2xxSuccessful();
    }

    public ResponseEntity<String> buscarGeoJson(String nomeTabela) {
        String url = UriComponentsBuilder
                .fromUri(URI.create("/"+workspace +"/ows"))
                .queryParam("service", "WFS")
                .queryParam("version", "1.3.0")
                .queryParam("request", "GetFeature")
                .queryParam("typeName", workspace +":"+nomeTabela)
                .queryParam("maxFeatures", 100)
                .queryParam("outputFormat", "application/json")
                .toUriString();
        Logger.getLogger("GeoServer").info(url);

        return restClient.get().uri(url).retrieve().toEntity(String.class);
    }

}
