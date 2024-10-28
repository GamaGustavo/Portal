package ifs.edu.br.portal.repository;

import ifs.edu.br.portal.entity.PontoTempoShapeFile;
import ifs.edu.br.portal.entity.PontoTempoShapeFileId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PontoTempoShapeFileRepository extends JpaRepository<PontoTempoShapeFile, PontoTempoShapeFileId> {
}
