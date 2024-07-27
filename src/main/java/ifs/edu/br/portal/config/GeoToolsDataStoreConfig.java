package ifs.edu.br.portal.config;

import org.geotools.api.data.DataStore;
import org.geotools.api.data.DataStoreFinder;
import org.geotools.data.postgis.PostgisNGDataStoreFactory;
import org.postgresql.ds.PGSimpleDataSource;
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
        PGSimpleDataSource dataSource1 = (PGSimpleDataSource) dataSource;
        params.put(PostgisNGDataStoreFactory.DBTYPE.key, PostgisNGDataStoreFactory.DBTYPE.sample);
       params.put(PostgisNGDataStoreFactory.USER.key, dataSource1.getUser());
        params.put(PostgisNGDataStoreFactory.PASSWD.key, dataSource1.getPassword());
        params.put(PostgisNGDataStoreFactory.HOST.key, dataSource1.getServerNames()[0]);
        params.put(PostgisNGDataStoreFactory.PORT.key, dataSource1.getPortNumbers()[0]);
        params.put(PostgisNGDataStoreFactory.DATABASE.key, dataSource1.getDatabaseName());
        params.put(PostgisNGDataStoreFactory.SCHEMA.key, "shape_files");
        return DataStoreFinder.getDataStore(params);
    }
}
