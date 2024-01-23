package edu.fiis.sisa.repository;

import edu.fiis.sisa.entities.CursosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursosRepository  extends JpaRepository<CursosEntity, Integer> {

    CursosEntity findByNombreDelCurso(String nombreDelCurso);
}
