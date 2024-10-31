package ifs.edu.br.portal.controller;

import ifs.edu.br.portal.entity.PontoTempo;
import ifs.edu.br.portal.service.PontoTempoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ponto-tempo")
public class PontoTempoController {


    private final PontoTempoService pontoTempoService;

    public PontoTempoController(PontoTempoService pontoTempoService) {
        this.pontoTempoService = pontoTempoService;
    }

    @PostMapping
    public ResponseEntity<Integer> cadastrar(@RequestBody PontoTempo novaPontoTempo) {
        return ResponseEntity.ok(pontoTempoService.cadastrar(novaPontoTempo));
    }

    @PutMapping
    public ResponseEntity<PontoTempo> editar(@RequestBody PontoTempo pontoTempo) {
        return ResponseEntity.of(pontoTempoService.editar(pontoTempo));
    }

    @DeleteMapping
    public ResponseEntity<PontoTempo> deletar(@RequestParam Integer id) {
        return ResponseEntity.of(pontoTempoService.deletar(id));
    }

    @GetMapping
    public ResponseEntity<List<PontoTempo>> listarPorMapa(@RequestParam Integer idMapa) {
        return ResponseEntity.ok(pontoTempoService.listarPorMapa(idMapa));
    }

}
