package ifs.edu.br.portal.service;

import ifs.edu.br.portal.entity.PontoTempoShapeFile;
import ifs.edu.br.portal.entity.PontoTempoShapeFileId;
import ifs.edu.br.portal.repository.PontoTempoRepository;
import ifs.edu.br.portal.entity.PontoTempo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PontoTempoService {

    private final PontoTempoRepository repository;
    private final PontoTempoShapeFileService shapeFileService;

    public PontoTempoService(PontoTempoRepository repository, PontoTempoShapeFileService shapeFileService) {
        this.repository = repository;
        this.shapeFileService = shapeFileService;
    }

    @Transactional
    public Integer cadastrar(PontoTempo novaPontoTempo) {
        var newPontoTempo = new PontoTempo();
        newPontoTempo.setData(novaPontoTempo.getData());
        newPontoTempo.setNome(novaPontoTempo.getNome());
        newPontoTempo.setDescricao(novaPontoTempo.getDescricao());
        newPontoTempo.setDocumentos(novaPontoTempo.getDocumentos());
        newPontoTempo =  repository.save(newPontoTempo);

        for (PontoTempoShapeFile ps :  novaPontoTempo.getPontoTempoShapeFiles()){
            ps.setPontoTempo(newPontoTempo);
            var id = new PontoTempoShapeFileId();
            id.setPontoTempoId(newPontoTempo.getId());
            id.setShapeFileId(ps.getShapeFile().getId());
            ps.setId(id);
            shapeFileService.salvar(ps);
        }

        return newPontoTempo.getId();

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
