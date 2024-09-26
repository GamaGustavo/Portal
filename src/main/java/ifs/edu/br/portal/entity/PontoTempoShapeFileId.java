package ifs.edu.br.portal.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


@Embeddable
@Getter
@Setter
public class PontoTempoShapeFileId implements Serializable {
   private Integer shapeFileId;
   private Integer pontoTempoId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PontoTempoShapeFileId that = (PontoTempoShapeFileId) o;
        return Objects.equals(shapeFileId, that.shapeFileId) && Objects.equals(pontoTempoId, that.pontoTempoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shapeFileId, pontoTempoId);
    }
}