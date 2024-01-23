package edu.fiis.sisa.repository;

import edu.fiis.sisa.entities.ClasesEntity;
import edu.fiis.sisa.entities.CursosDocentesEntity;
import edu.fiis.sisa.entities.DocenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClasesRepository extends JpaRepository<ClasesEntity, Integer> {

    List<ClasesEntity> findAllByDocente(DocenteEntity docente);

    List<ClasesEntity> findByCursoDocente_Id(Integer cursoDocenteId);

}
