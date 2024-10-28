package ifs.edu.br.portal.controller;

import ifs.edu.br.portal.entity.Categoria;
import ifs.edu.br.portal.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<Integer> cadastrar(@RequestBody Categoria novaCategoria) {
        return ResponseEntity.ok(categoriaService.cadastrar(novaCategoria));
    }

    @PutMapping
    public ResponseEntity<Categoria> editar(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.editar(categoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> listarTodos() {
        return ResponseEntity.ok(categoriaService.listarTodos());
    }
}
