package ifs.edu.br.portal.service;

import ifs.edu.br.portal.entity.ShapeFile;
import ifs.edu.br.portal.exception.ResourceNotFoundException;
import ifs.edu.br.portal.http.GeoServerWebclient;
import ifs.edu.br.portal.repository.ShapeFileRepository;
import ifs.edu.br.portal.util.Utils;
import org.geotools.api.data.DataStore;
import org.geotools.api.data.SimpleFeatureStore;
import org.geotools.api.data.Transaction;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.feature.FeatureCollection;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ShapeFileService {
    private final ShapeFileRepository repository;

    private final DataStore dataStore;

    private final GeoServerWebclient geoServerWebclient;

    public ShapeFileService(ShapeFileRepository repository, DataStore dataStore, GeoServerWebclient geoServerWebclient) {
        this.repository = repository;
        this.dataStore = dataStore;
        this.geoServerWebclient = geoServerWebclient;
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

    private String uploadShapefile(MultipartFile file) {
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
            var nativeName = tempFile.getFileName().toString();
            shapefileDataStore.dispose();
            // Deleta o arquivo temporário
            Files.delete(tempFile);

            return nativeName.replace("/temp/", "").replace(".shp", "");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar o shapefile: " + e.getMessage());
        }
    }

    private static Path getTempFile(MultipartFile file) throws IOException {
        // Salva o arquivo no disco temporariamente
        var fileName = file.getOriginalFilename();
        fileName = fileName.replaceAll(" ", "_");
        fileName = fileName.toLowerCase();
        fileName = Utils.removerAcentos(fileName);
        Path tempFile = Files.createTempFile(fileName.replace(".shp", ""), ".shp");
        Files.write(tempFile, file.getBytes());
        return tempFile;
    }

    public List<ShapeFile> cadastrar(List<MultipartFile> files) {
        var shapes = files.stream().map(multipartFile -> {
            var titulo = Objects.requireNonNull(multipartFile.getOriginalFilename()).replace(".shp", "");
            var nomeTabela = uploadShapefile(multipartFile);
            var status = geoServerWebclient.publicarShape(
                    nomeTabela,
                    titulo);
            var shape = new ShapeFile();
            shape.setTitulo(titulo);
            shape.setNomeTaleba(nomeTabela);
            shape.setPublicado(status);
            return shape;
        }).toList();
        return repository.saveAll(shapes);
    }
    public ResponseEntity<String> buscarGeoJson(Integer id) {
      ShapeFile shapeFile = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ShapeFile não encontado."));
      if(!shapeFile.isPublicado()) throw new ResourceNotFoundException("ShapeFile não publicado.");
      return  geoServerWebclient.buscarGeoJson(shapeFile.getNomeTaleba());
    }
}

