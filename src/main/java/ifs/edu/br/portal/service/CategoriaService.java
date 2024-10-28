package ifs.edu.br.portal.service;

import ifs.edu.br.portal.exception.ResourceNotFoundException;
import ifs.edu.br.portal.repository.CategoriaRepository;
import ifs.edu.br.portal.entity.Categoria;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

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
    public Categoria editar(Categoria categoria) {
        Optional<Categoria> categoriaOptional = repository.findById(categoria.getId());
        if (categoriaOptional.isPresent()) {
            BeanUtils.copyProperties(categoria, categoriaOptional.get(), "id");
            return repository.save(categoriaOptional.get());
        }
        throw new ResourceNotFoundException("Nenhuma categoria encontrada.");
    }

    @Transactional
    public void deletar(Integer id) throws ResourceNotFoundException {
        Optional<Categoria> categoriaOptional = repository.findById(id);
        categoriaOptional.ifPresentOrElse(repository::delete, () -> {
            throw new ResourceNotFoundException("Nenhuma categoria encontrada.");
        });

    }


    public List<Categoria> listarTodos() {
        return repository.findAll();
    }

}
