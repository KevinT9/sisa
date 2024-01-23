package edu.fiis.sisa.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String tipoDeUsuario;
    private String email;
    private String celular;
    private String password;
    private String foto;
}
