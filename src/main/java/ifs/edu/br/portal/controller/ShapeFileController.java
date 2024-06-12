package ifs.edu.br.portal.controller;

import ifs.edu.br.portal.entity.ShapeFile;
import ifs.edu.br.portal.service.ShapeFileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ShapeFileController {

    private final ShapeFileService shapeFileService;

    public ShapeFileController(ShapeFileService shapeFileService) {
        this.shapeFileService = shapeFileService;
    }


    @PostMapping("/uploadShapefile")
    public String uploadShapefile(@RequestBody MultipartFile file)  {
        if (file != null && !file.isEmpty()) {
            return shapeFileService.uploadShapefile(file);
        }
        return "O arquivo está vazio";
    }

    @PostMapping
    public ResponseEntity<Integer> cadastrar(@RequestBody ShapeFile shapeFile) {
        return ResponseEntity.ok(shapeFileService.cadastrar(shapeFile));
    }

    @DeleteMapping
    public ResponseEntity<ShapeFile> deletar(@RequestParam Integer id) {
        ShapeFile shapeFile = shapeFileService.deletar(id).orElseThrow();
        return ResponseEntity.ok(shapeFile);
    }

    @GetMapping
    public ResponseEntity<List<ShapeFile>> listar(@RequestParam Integer pontTempo) {
        return ResponseEntity.ok(shapeFileService.listarPorPonto(pontTempo));
    }
}
