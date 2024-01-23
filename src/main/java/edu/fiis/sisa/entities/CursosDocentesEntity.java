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
@Table(name = "cursos_docentes")
public class CursosDocentesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private CursosEntity curso;

    @ManyToOne
    private DocenteEntity docente;

    private String seccion;
    private String turno;
    private String modalidad;

}
