package ifs.edu.br.portal;

import ifs.edu.br.portal.service.GeoServerApi;
import org.geotools.api.data.DataStore;
import org.geotools.api.data.DataStoreFinder;
import org.geotools.api.data.FeatureSource;
import org.geotools.api.data.SimpleFeatureStore;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.geotools.data.postgis.PostgisNGDataStoreFactory;
import org.geotools.feature.FeatureCollection;
import org.geotools.util.URLs;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class PortalApplicationTests {

    @Test
    void contextLoads() {

    }
    public static void main(String[] args) throws Exception {
        GeoServerApi geoServerApi = new GeoServerApi("http://localhost:8080/geoserver", "admin", "geoserver");
        /*geoServerApi.createWorkspace("myWorkspace3");

        geoServerApi.createPostGISDataStore("myWorkspace3", "myDataStore3", "172.18.0.3", 5432, "mydatabase", "myuser", "secret");
*/
        String layerUrl = geoServerApi.createLayer("myWorkspace3", "myDataStore3", "BR_UF_202214893172114710448431", "BR_UF_202214893172114710448431");
        System.out.println("Layer URL: " + layerUrl);

        String layers = geoServerApi.listLayers();
        System.out.println("Layers: " + layers);


    }

}
