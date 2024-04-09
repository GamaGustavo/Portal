package ifs.edu.br.portal.Repository;

import ifs.edu.br.portal.entity.Categoria;
import ifs.edu.br.portal.entity.Mapa;
import ifs.edu.br.portal.entity.PontoTempo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapaRepository extends JpaRepository<Mapa, Integer> {


    List<Mapa> findAllByIdOrNomeOrCategoriasOrPontoTemposOrderByNome(
            Integer id, String nome, List<Categoria> categorias, List<PontoTempo> pontoTempos);

}