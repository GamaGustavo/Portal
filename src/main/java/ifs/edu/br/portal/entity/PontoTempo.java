package ifs.edu.br.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class PontoTempo {
    @Id
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
