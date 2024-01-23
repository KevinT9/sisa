package edu.fiis.sisa.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "Usuarios")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String genero;
    private String tipoDeUsuario;
    private String email;
    private String celular;
    private String direccion;
    private String password;
    private String dni;
    @Column(columnDefinition = "TEXT")
    private String foto;
    private Date fechaRegistro;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean estado;

}
