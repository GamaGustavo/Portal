package ifs.edu.br.portal.repository;

import ifs.edu.br.portal.entity.ShapeFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShapeFileRepository extends JpaRepository<ShapeFile, Integer> {
    @Query(nativeQuery = true, value = "select sf.* from shape_file sf " +
            "  join ponto_tempo_shape_files ptsf on ptsf.shape_file_id = sf.id " +
            "  join ponto_tempo pt on ptsf.ponto_tempo_id = pt.id where pt.id = ?1")
    List<ShapeFile> findAllByPontoTempo(Integer id);

    @Query(nativeQuery = true,
            value = "select shape.* from shape_file shape " +
                    "join ponto_tempo_shape_file pts on pts.shape_file_id = shape.id " +
                    "join ponto_tempo pt on pt.id = pts.ponto_tempo_id " +
                    "join mapa_ponto_tempos mpt on pt.id = mpt.ponto_tempos_id " +
                    "where mpt.mapa_id = ?1 order by pt.data desc LIMIT 1")
    ShapeFile findByMapId(Integer id);
}