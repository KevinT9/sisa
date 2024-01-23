package edu.fiis.sisa.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String tipoDeUsuario;
    private String email;
    private String password;
}
