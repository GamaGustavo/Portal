package ifs.edu.br.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;
@Data
@Entity
public class Mapa {
    @Id
    private Long id;

    @Column(length = 200)
    private String nome;

    @ManyToMany
    private List<Categoria> categorias;

    @ManyToMany
    private List<PontoTempo> pontoTempos;

}
