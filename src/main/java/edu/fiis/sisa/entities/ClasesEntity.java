package edu.fiis.sisa.entities;

import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "clases")
public class ClasesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_del_curso")
    private CursosEntity curso;

    @ManyToOne
    @JoinColumn(name = "id_del_docente")
    private DocenteEntity docente;

    @ManyToOne
    @JoinColumn(name = "relacion_curso_docente")
    private CursosDocentesEntity cursoDocente;

    private Date fecha;
    private Time horaDeInicio;
    private Time horaDeFinalizacion;
    private String ubicacion;
    private String descripcion;
    private String dia;

}
