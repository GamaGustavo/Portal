package ifs.edu.br.portal.service;
import ifs.edu.br.portal.repository.ShapeFileRepository;
import ifs.edu.br.portal.entity.ShapeFile;
import org.geotools.api.data.DataStore;
import org.geotools.api.data.SimpleFeatureStore;
import org.geotools.api.data.Transaction;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.feature.FeatureCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class ShapeFileService {
    @Autowired
    private ShapeFileRepository repository;
    @Autowired
    private DataStore dataStore;

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


    public String uploadShapefile(MultipartFile file){
        try {
            // Salva o arquivo no disco temporariamente
            Path tempFile = Files.createTempFile("shapefile", ".shp");
            Files.write(tempFile, file.getBytes());

            // Cria o ShapefileDataStore
            ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
            ShapefileDataStore shapefileDataStore = (ShapefileDataStore) dataStoreFactory.createDataStore(tempFile.toUri().toURL());

            // Lê as features do shapefile
            FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection = shapefileDataStore.getFeatureSource().getFeatures();

            // Adiciona as features no DataStore (PostGIS)
            SimpleFeatureStore featureStore = (SimpleFeatureStore) dataStore.getFeatureSource(file.getName());
            featureStore.setTransaction(Transaction.AUTO_COMMIT);
            featureStore.addFeatures(featureCollection);

            // Deleta o arquivo temporário
            Files.delete(tempFile);

            return "Shapefile carregado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao carregar o shapefile: " + e.getMessage();
        }
    }
}
