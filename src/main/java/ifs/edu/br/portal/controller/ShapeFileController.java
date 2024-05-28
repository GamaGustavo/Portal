package ifs.edu.br.portal.controller;

import ifs.edu.br.portal.service.ShapeFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ShapeFileController {
    @Autowired
  private  ShapeFileService shapeFileService;


    @PostMapping("/uploadShapefile")
    public String uploadShapefile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "O arquivo está vazio";
        }
       return shapeFileService.uploadShapefile(file);
    }
}
