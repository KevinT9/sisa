package edu.fiis.sisa.dto;

import edu.fiis.sisa.entities.CursosEntity;
import edu.fiis.sisa.entities.DocenteEntity;
import lombok.Data;

@Data
public class CursoDTO {
    private CursosEntity cursosEntity;
    private DocenteEntity docente;
}
