package ifs.edu.br.portal.controller;

import ifs.edu.br.portal.service.GeoServerApi;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("geoserver")
public class GeoSrverController {

    private final GeoServerApi geoServerApi;

    public GeoSrverController(GeoServerApi geoServerApi) {
        this.geoServerApi = geoServerApi;
    }

    @PostMapping("/workspace")
    public Mono<String> createWorkspace(@RequestParam String workspaceName) {
        return geoServerApi.createWorkspace(workspaceName);
    }
    @PostMapping("/datastore")
    public Mono<String> createPostgisDatastore(@RequestParam String workspaceName,@RequestParam String datastoreName) {
        return geoServerApi.createPostgisDatastore(workspaceName, datastoreName);
    }
    @PostMapping("/layer")
    public Mono<String> createLayer(@RequestParam String workspaceName, @RequestParam String datastoreName,@RequestParam String layerName,@RequestParam String nativeName) {
        return geoServerApi.createLayer(workspaceName, datastoreName, layerName, nativeName);
    }
    @GetMapping("/layer")
    public Mono<String> listLayers(@RequestParam String workspaceName) {
        return geoServerApi.listLayers(workspaceName);
    }
}
