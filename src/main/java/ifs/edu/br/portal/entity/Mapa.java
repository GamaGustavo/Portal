package ifs.edu.br.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
public class Mapa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String nome;

    @ManyToMany
    private List<Categoria> categorias;

    @ManyToMany
    private List<PontoTempo> pontoTempos;

}
