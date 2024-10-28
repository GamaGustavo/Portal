package ifs.edu.br.portal.controller;

import ifs.edu.br.portal.service.PontoTempoShapeFileService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("ponto-tempo-shape-file")
public class PontoTempoShapeFileController {

    private final PontoTempoShapeFileService pontoTempoShapeFileService;

    public PontoTempoShapeFileController(PontoTempoShapeFileService pontoTempoShapeFileService) {
        this.pontoTempoShapeFileService = pontoTempoShapeFileService;
    }



}
