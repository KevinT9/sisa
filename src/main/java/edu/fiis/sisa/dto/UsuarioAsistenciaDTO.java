package edu.fiis.sisa.dto;

import edu.fiis.sisa.entities.AsistenciaEntity;
import lombok.Data;

@Data
public class UsuarioAsistenciaDTO {
    private Integer idClase;
    private String nombreCompleto;
    private String email;
    private boolean presente;
    private boolean tardanza;
    private boolean falta;
}