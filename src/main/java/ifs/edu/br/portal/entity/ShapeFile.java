package ifs.edu.br.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ShapeFile  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 200)
    public String caminhoArquivo;
}
