package ifs.edu.br.portal.entity;

import jakarta.persistence.*;
import lombok.Data;



@Data
@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String nome;


}
