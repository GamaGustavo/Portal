package ifs.edu.br.portal.repository;

import ifs.edu.br.portal.entity.PontoTempo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PontoTempoRepository extends JpaRepository<PontoTempo, Integer> {
    @Query(nativeQuery = true, value = "select pt.* from ponto_tempo pt "+
            "  join mapa_ponto_tempos mpt on mpt.ponto_tempos_id = pt.id " +
            "  join mapa map on map.id = mpt.mapa_id where map.id = ?1")
    List<PontoTempo> findAllByMapa(Integer idMapa);
}