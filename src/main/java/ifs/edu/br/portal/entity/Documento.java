package ifs.edu.br.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Documento {
    @Id
    private Long id;
    public TipoArquivo tipoArquivo;
    @Column(length = 200)
    public String caminhoArquivo;


}
