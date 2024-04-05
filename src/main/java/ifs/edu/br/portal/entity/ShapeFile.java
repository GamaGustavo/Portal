package ifs.edu.br.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ShapeFile  {
    @Id
    private Long id;
    @Column(length = 200)
    public String caminhoArquivo;
}
