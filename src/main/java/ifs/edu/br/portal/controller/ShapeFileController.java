package ifs.edu.br.portal.controller;

import ifs.edu.br.portal.entity.ShapeFile;
import ifs.edu.br.portal.exception.BadRequestException;
import ifs.edu.br.portal.service.ShapeFileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("shape-file")
public class ShapeFileController {

    private final ShapeFileService shapeFileService;

    public ShapeFileController(ShapeFileService shapeFileService) {
        this.shapeFileService = shapeFileService;
    }


    @PostMapping("/uploadShapefile")
    public ResponseEntity<List<ShapeFile>> cadastrar(@RequestBody List<MultipartFile> files)  {
        if (files != null && !files.isEmpty()){
            return ResponseEntity.ok(shapeFileService.cadastrar(files));
        }
        throw new BadRequestException("Nenhum ShapeFile encontrado no corpo da requisição.");
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
