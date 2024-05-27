package ifs.edu.br.portal.repository;

import ifs.edu.br.portal.entity.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Integer> {
    @Query(nativeQuery = true, value = "select doc.* from documento doc " +
            "  join ponto_tempo_documentos ptdoc on ptdoc.documentos_id = doc.id " +
            "  join ponto_tempo pt on ptdoc.ponto_tempo_id = pt.id where pt.id = ?1")
    List<Documento> findAllByPontoTempo(Integer id);
}