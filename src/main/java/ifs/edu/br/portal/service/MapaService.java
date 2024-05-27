package ifs.edu.br.portal.service;

import ifs.edu.br.portal.repository.MapaRepository;
import ifs.edu.br.portal.entity.Mapa;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MapaService {
    private final MapaRepository repository;

    public MapaService(MapaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Integer cadastrar(Mapa obj) {
        return repository.save(obj).getId();
    }

    @Transactional
    public Optional<Mapa> editar(Mapa obj) {
        Optional<Mapa> opMap = repository.findById(obj.getId());
        if (opMap.isPresent()) {
            BeanUtils.copyProperties(obj, opMap.get(), "id");
            return Optional.of(repository.save(opMap.get()));
        }
        return opMap;
    }

    @Transactional
    public Optional<Mapa> deletar(Integer id) {
        Optional<Mapa> opMap = repository.findById(id);
        opMap.ifPresent((value) -> repository.deleteById(value.getId()));
        return opMap;
    }


    public List<Mapa> listarTodos() {
        return repository.findAll();
    }

    public List<Mapa> filtrar(Mapa mapa) {
        return repository
                .findAllByIdOrNomeOrCategoriasOrPontoTemposOrderByNome(
                        mapa.getId(),
                        mapa.getNome(),
                        mapa.getCategorias(),
                        mapa.getPontoTempos());
    }
}
