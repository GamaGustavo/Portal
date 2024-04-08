package ifs.edu.br.portal.Repository;

import ifs.edu.br.portal.entity.ShapeFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShapeFileRepository extends JpaRepository<ShapeFile, Integer> {
}