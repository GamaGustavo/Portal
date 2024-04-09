package ifs.edu.br.portal.service;

import ifs.edu.br.portal.Repository.PontoTempoRepository;
import ifs.edu.br.portal.entity.PontoTempo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PontoTempoService {

    private final PontoTempoRepository repository;

    public PontoTempoService(PontoTempoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Integer cadastrar(PontoTempo novaPontoTempo) {
        return repository.save(novaPontoTempo).getId();
    }

    @Transactional
    public Optional<PontoTempo> editar(PontoTempo pontoTempo){
        Optional<PontoTempo> opPT = repository.findById(pontoTempo.getId());
        if (opPT.isPresent()){
            BeanUtils.copyProperties(pontoTempo, opPT.get(), "id");
            return Optional.of(repository.save(opPT.get()));
        }
        return opPT;
    }

    @Transactional
    public Optional<PontoTempo> deletar(Integer id) {
        Optional<PontoTempo> opPT = repository.findById(id);
        opPT.ifPresent(repository::delete);
        return opPT;
    }


    public List<PontoTempo> listarPorMapa(Integer idMapa) {
        return repository.findAllByMapa(idMapa);
    }

}
