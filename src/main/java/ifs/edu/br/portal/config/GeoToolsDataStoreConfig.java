package ifs.edu.br.portal.config;

import org.geotools.api.data.DataStore;
import org.geotools.api.data.DataStoreFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
@Configuration
public class GeoToolsDataStoreConfig {
    @Bean
    public DataStore dataStore(DataSource dataSource) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("dbtype", "postgis");
        params.put("DataSource", dataSource);
        return DataStoreFinder.getDataStore(params);
    }
}
