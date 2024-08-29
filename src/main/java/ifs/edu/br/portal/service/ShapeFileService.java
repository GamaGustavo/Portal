package ifs.edu.br.portal.service;

import ifs.edu.br.portal.entity.ShapeFile;
import ifs.edu.br.portal.repository.ShapeFileRepository;
import org.geotools.api.data.*;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.geotools.api.filter.Filter;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.feature.FeatureCollection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ShapeFileService {
    private final ShapeFileRepository repository;

    private final DataStore dataStore;

    public ShapeFileService(ShapeFileRepository repository, DataStore dataStore) {
        this.repository = repository;
        this.dataStore = dataStore;
    }

    @Transactional
    public Integer cadastrar(ShapeFile obj) {
        return repository.save(obj).getId();
    }

    @Transactional
    public Optional<ShapeFile> deletar(Integer id) {
        Optional<ShapeFile> opShape = repository.findById(id);
        opShape.ifPresent(repository::delete);
        return opShape;
    }


    public List<ShapeFile> listarPorPonto(Integer idPonto) {
        return repository.findAllByPontoTempo(idPonto);
    }

    public String uploadShapefile(MultipartFile file) {
        try {
            Path tempFile = getTempFile(file);
/*
            // Cria o ShapefileDataStore
            DataStore dataTempFile = DataStoreFinder.getDataStore(Collections.singletonMap("url", tempFile.toUri().toURL()));

            // Lê as features do shapefile
            String inputTypeName = Arrays.stream(dataTempFile.getTypeNames()).findFirst().orElse("");
            SimpleFeatureType simpleFeatureType = dataTempFile.getSchema(inputTypeName);
            SimpleFeatureSource source = dataTempFile.getFeatureSource(inputTypeName);

            // Adiciona as features no DataStore (PostGIS)
            dataStore.createSchema(simpleFeatureType);
            SimpleFeatureStore featureStore = (SimpleFeatureStore) dataStore.getFeatureSource(inputTypeName);
            featureStore.addFeatures(source.getFeatures(Filter.INCLUDE));
            dataTempFile.dispose();*/

           ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
            ShapefileDataStore shapefileDataStore = (ShapefileDataStore) dataStoreFactory.createDataStore(tempFile.toUri().toURL());

            // Lê as features do shapefile
            FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection = shapefileDataStore.getFeatureSource().getFeatures();

            // Adiciona as features no DataStore (PostGIS)
            String inputTypeName = Arrays.stream(shapefileDataStore.getTypeNames()).findFirst().orElse("");
            SimpleFeatureType simpleFeatureType = shapefileDataStore.getSchema(inputTypeName);
            dataStore.createSchema(simpleFeatureType);
            SimpleFeatureStore featureStore = (SimpleFeatureStore) dataStore.getFeatureSource(inputTypeName);
            featureStore.setTransaction(Transaction.AUTO_COMMIT);
            featureStore.addFeatures(featureCollection);

            shapefileDataStore.dispose();
            // Deleta o arquivo temporário
            Files.delete(tempFile);

            return "Shapefile carregado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao carregar o shapefile: " + e.getMessage();
        }
    }

    private static Path getTempFile(MultipartFile file) throws IOException {
        // Salva o arquivo no disco temporariamente
        Path tempFile = Files.createTempFile(file.getOriginalFilename().replace(".shp",""), ".shp");
        Files.write(tempFile, file.getBytes());
        return tempFile;
    }

}

