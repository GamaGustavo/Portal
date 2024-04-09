package ifs.edu.br.portal.service;
import ifs.edu.br.portal.Repository.ShapeFileRepository;
import ifs.edu.br.portal.entity.ShapeFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ShapeFileService {
    private final ShapeFileRepository repository;
    public ShapeFileService(ShapeFileRepository repository) {
        this.repository = repository;
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

}
