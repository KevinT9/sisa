package edu.fiis.sisa.repository;

import edu.fiis.sisa.entities.CursosDocentesEntity;
import edu.fiis.sisa.entities.CursosEntity;
import edu.fiis.sisa.entities.DocenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursosDocentesRepository extends JpaRepository<CursosDocentesEntity, Integer> {

    List<CursosDocentesEntity> findAllByDocente(DocenteEntity docenteEntity);

    CursosDocentesEntity findByCursoAndDocente(CursosEntity cursosEntity, DocenteEntity docenteEntity);

    List<CursosDocentesEntity> findAllById(Integer id);

}
