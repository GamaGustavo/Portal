package ifs.edu.br.portal.controller;

import ifs.edu.br.portal.entity.Documento;
import ifs.edu.br.portal.service.DocumentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documento")
public class DocumentoController {

    private final DocumentoService documentoService;

    public DocumentoController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @PostMapping
    public ResponseEntity<Integer> cadastrar(@RequestBody Documento obj) {

        return ResponseEntity.ok(documentoService.cadastrar(obj));
    }

    @DeleteMapping
    public ResponseEntity<Documento> deletar(@RequestParam Integer id) {
        return ResponseEntity.of(documentoService.deletar(id));
    }

    @GetMapping
    public ResponseEntity<List<Documento>> listarPorPonto(@RequestParam Integer idPonto) {
        return ResponseEntity.ok(documentoService.listarPorPonto(idPonto));
    }


}
