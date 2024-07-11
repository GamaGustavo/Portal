package ifs.edu.br.portal.controller;

import ifs.edu.br.portal.entity.Categoria;
import ifs.edu.br.portal.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public Integer cadastrar(@RequestBody Categoria novaCategoria) {
        return categoriaService.cadastrar(novaCategoria);
    }

    @PutMapping
    public ResponseEntity<Categoria> editar(@RequestBody Categoria categoria) {
        return ResponseEntity.of(categoriaService.editar(categoria));
    }

    @DeleteMapping
    public ResponseEntity<Categoria> deletar(@RequestParam Integer id) {
        return ResponseEntity.of(categoriaService.deletar(id));
    }

    @GetMapping()
    public ResponseEntity<List<Categoria>> listarTodos() {
        return ResponseEntity.ok(categoriaService.listarTodos());
    }
}
