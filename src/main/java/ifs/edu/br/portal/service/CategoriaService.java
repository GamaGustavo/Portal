package ifs.edu.br.portal.service;

import ifs.edu.br.portal.repository.CategoriaRepository;
import ifs.edu.br.portal.entity.Categoria;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService  {

    public final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Integer cadastrar(Categoria novaCategoria) {
        Categoria categoria = repository.save(novaCategoria);
        return categoria.getId();

    }

    @Transactional
    public Optional<Categoria> editar(Categoria categoria){
        Optional<Categoria> categoriaOptional = repository.findById(categoria.getId());
        if (categoriaOptional.isPresent()){
            BeanUtils.copyProperties(categoria, categoriaOptional.get(), "id");
            return Optional.of(repository.save(categoriaOptional.get()));
        }
        return categoriaOptional;
    }

    @Transactional
    public Optional<Categoria> deletar(Integer id) {
        Optional<Categoria> categoriaOptional = repository.findById(id);
        categoriaOptional.ifPresent(repository::delete);
        return categoriaOptional;
    }


    public List<Categoria> listarTodos() {
        return repository.findAll();
    }

}
