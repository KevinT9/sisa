package edu.fiis.sisa.dto;

import edu.fiis.sisa.entities.CursosDocentesEntity;
import edu.fiis.sisa.entities.CursosEntity;
import lombok.Data;

import java.util.List;

@Data
public class MatriculaDTO {

    private List<CursosEntity> listaCursos;
    private List<CursosDocentesEntity> listaCursosDocentes;

}
