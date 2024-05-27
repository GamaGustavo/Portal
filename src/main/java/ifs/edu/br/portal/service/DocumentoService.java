package ifs.edu.br.portal.service;

import ifs.edu.br.portal.repository.DocumentoRepository;
import ifs.edu.br.portal.entity.Documento;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class DocumentoService{
    private final DocumentoRepository repository;

    public DocumentoService(DocumentoRepository repository) {
        this.repository = repository;
    }
    @Transactional
    public Integer cadastrar(Documento obj) {
        return repository.save(obj).getId();
    }

    @Transactional
    public Optional<Documento> deletar(Integer id) {
        Optional<Documento> opDoc = repository.findById(id);
        opDoc.ifPresent(repository::delete);
        return opDoc;
    }

    public List<Documento> listarPorPonto(Integer idPonto) {
        return repository.findAllByPontoTempo(idPonto);
    }
}
