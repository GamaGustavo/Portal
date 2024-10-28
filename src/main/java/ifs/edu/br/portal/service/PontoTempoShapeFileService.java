package ifs.edu.br.portal.service;

import ifs.edu.br.portal.entity.PontoTempoShapeFile;
import ifs.edu.br.portal.repository.PontoTempoShapeFileRepository;
import org.springframework.stereotype.Service;

@Service
public class PontoTempoShapeFileService {

    private final PontoTempoShapeFileRepository repository;


    public PontoTempoShapeFileService(PontoTempoShapeFileRepository repository) {
        this.repository = repository;
    }

    public PontoTempoShapeFile salvar(PontoTempoShapeFile pontoTempoShapeFile) {
        return repository.save(pontoTempoShapeFile);
    }
    
    public PontoTempoShapeFile atualizar(PontoTempoShapeFile pontoTempoShapeFile) {
        return repository.save(pontoTempoShapeFile);
    }



}
