package ifs.edu.br.portal.Repository;

import ifs.edu.br.portal.entity.Mapa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface MapaRepository extends JpaRepository<Mapa, Integer> {
}