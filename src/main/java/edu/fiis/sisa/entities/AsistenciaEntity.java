package edu.fiis.sisa.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "asistencia")
public class AsistenciaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_del_usuario")
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "id_de_la_clase")
    private ClasesEntity clase;

    private LocalDateTime fechaDeAsistencia;
    private Boolean presente;
    private Boolean tardanza;
    private Boolean falta;

}
