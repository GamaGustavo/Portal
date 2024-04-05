package ifs.edu.br.portal.entity;

import lombok.Getter;

@Getter
public enum TipoArquivo {
    LINK(1),
    DOCUMENTO(2),
    IMAGEM(3);

    private final Integer VALLUE;

    TipoArquivo(Integer value) {
        this.VALLUE = value;
    }
}
