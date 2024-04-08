package ifs.edu.br.portal.Repository;

import ifs.edu.br.portal.entity.PontoTempo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PontoTempoRepository extends JpaRepository<PontoTempo, Integer> {
}