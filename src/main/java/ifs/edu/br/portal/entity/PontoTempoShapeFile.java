package ifs.edu.br.portal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
public class PontoTempoShapeFile {

    @EmbeddedId
    private PontoTempoShapeFileId id;
    private Integer shapeFileOrdem;

    @ManyToOne
    @MapsId("pontoTempoId")
    @JoinColumn(name = "ponto_tempo_id")
    private PontoTempo pontoTempo;

    @ManyToOne
    @MapsId("shapeFileId")
    @JoinColumn(name = "shape_file_id")
    private ShapeFile shapeFile;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PontoTempoShapeFile that = (PontoTempoShapeFile) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}


