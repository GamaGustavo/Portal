package ifs.edu.br.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class PontoTempo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 60)
    public String nome;
    @Column(columnDefinition = "text", length = 1000)
    public String descricao;
    @ManyToMany
    public List<ShapeFile> shapeFiles;
    @ManyToMany
    public List<Documento> documentos;
}
