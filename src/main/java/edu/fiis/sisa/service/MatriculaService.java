package edu.fiis.sisa.service;

import edu.fiis.sisa.entities.CursosDocentesEntity;
import edu.fiis.sisa.entities.MatriculaEntity;

public interface MatriculaService {

    boolean existeLaMatricula(MatriculaEntity matriculaEntity);

    MatriculaEntity save(MatriculaEntity matriculaEntity);

    Integer countByCursosDocentesAndCurrentYear(CursosDocentesEntity cursosDocentes);

}
