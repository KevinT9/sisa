package edu.fiis.sisa.entities;

import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "cursos")
public class CursosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombreDelCurso;
    private String descripcion;
    private String ciclo;
    private String codigo;
    private String urlFoto;
    private String planEstudios;
    private Integer minAlumnos;
    private Integer maxAlumnos;
    @Column(columnDefinition = "TEXT")
    private String fotoBase64;

}
