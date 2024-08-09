package ifs.edu.br.portal.controller;

import ifs.edu.br.portal.entity.Mapa;
import ifs.edu.br.portal.service.MapaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mapa")
public class MpaController {

    private final MapaService mapaService;

    public MpaController(MapaService documentoService) {
        this.mapaService = documentoService;
    }

    @PostMapping
    public ResponseEntity<Integer> cadastrar(@RequestBody Mapa obj) {
        return ResponseEntity.ok(mapaService.cadastrar(obj));
    }

    @PutMapping
    public ResponseEntity<Mapa> editar(@RequestBody Mapa obj) {
        return ResponseEntity.of(mapaService.editar(obj));
    }

    @DeleteMapping
    public ResponseEntity<Mapa> deletar(@RequestParam Integer id) {
        return ResponseEntity.of(mapaService.deletar(id));
    }

    @GetMapping()
    public ResponseEntity<List<Mapa>> listarTodos() {
        return ResponseEntity.ok(mapaService.listarTodos());
    }

    @GetMapping("filtrar-nome")
    public List<Mapa> filtrar(@RequestParam String nome) {
        var mapa = new Mapa();
        mapa.setNome(nome);
        return mapaService.filtrar(mapa);
    }

    @GetMapping("filtrar-id")
    public List<Mapa> filtrar(@RequestParam Integer id) {
        var mapa = new Mapa();
        mapa.setId(id);
        return mapaService.filtrar(mapa);
    }
}
