package ifs.edu.br.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Documento {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer id;
    public TipoArquivo tipoArquivo;
    @Column(length = 200)
    public String caminhoArquivo;


}
