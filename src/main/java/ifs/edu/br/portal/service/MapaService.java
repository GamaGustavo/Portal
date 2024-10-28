package ifs.edu.br.portal.service;

import ifs.edu.br.portal.exception.ResourceNotFoundException;
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
    private final GeoServerApi geoServerApi;

    public MapaService(MapaRepository repository, GeoServerApi geoServerApi) {
        this.repository = repository;
        this.geoServerApi = geoServerApi;
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
    public void deletar(Integer id) throws ResourceNotFoundException {
        Optional<Mapa> opMap = repository.findById(id);
        opMap.ifPresentOrElse((value) -> repository.deleteById(value.getId()),
                () -> {throw new ResourceNotFoundException("Não foi encontrado um mapa com o id " + id);});
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
