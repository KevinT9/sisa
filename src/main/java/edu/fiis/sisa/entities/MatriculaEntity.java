package edu.fiis.sisa.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "matricula")
public class MatriculaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "cursos_docentes_id")
    private CursosDocentesEntity cursosDocentes;


    private Date fechaRegistro;
    private LocalDateTime timestamp;

    @Column(name = "encargado_matricula_id")
    private String encargadoMatricula;

}
