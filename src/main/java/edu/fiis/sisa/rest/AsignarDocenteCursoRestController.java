package edu.fiis.sisa.rest;

import edu.fiis.sisa.entities.CursosDocentesEntity;
import edu.fiis.sisa.entities.CursosEntity;
import edu.fiis.sisa.entities.DocenteEntity;
import edu.fiis.sisa.repository.CursosDocentesRepository;
import edu.fiis.sisa.repository.CursosRepository;
import edu.fiis.sisa.repository.DocenteRepository;
import edu.fiis.sisa.service.UsuarioService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/asignar")
@Log4j2
public class AsignarDocenteCursoRestController {

    @Autowired
    private CursosRepository cursosRepository;
    @Autowired
    private DocenteRepository docenteRepository;
    @Autowired
    private CursosDocentesRepository cursosDocentesRepository;

    @PostMapping("/docente/curso")
    public ResponseEntity<String> saveDocenteCurso(
            @RequestParam Integer idCurso,
            @RequestParam Integer idDocente,
            @RequestParam String seccion,
            @RequestParam String turno,
            @RequestParam String modalidad
    ) {

        Optional<CursosEntity> cursosEntity = cursosRepository.findById(idCurso);
        Optional<DocenteEntity> docenteEntity = docenteRepository.findById(idDocente);

        if (cursosEntity.isPresent() && docenteEntity.isPresent()) {

            CursosDocentesEntity cursosDocentesEntity = new CursosDocentesEntity();
            cursosDocentesEntity.setCurso(cursosEntity.get());
            cursosDocentesEntity.setDocente(docenteEntity.get());
            cursosDocentesEntity.setSeccion(seccion);
            cursosDocentesEntity.setTurno(turno);
            cursosDocentesEntity.setModalidad(modalidad);

            cursosDocentesEntity = cursosDocentesRepository.save(cursosDocentesEntity);

            log.info("Se asigno el docente " + cursosDocentesEntity.getDocente().getUsuario().getNombre() +
                    " al curso " + cursosDocentesEntity.getCurso().getNombreDelCurso());
        } else {
            log.info("No se pudo guardar la relación");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/docente/curso/{idCurso}")
    public void delete(@PathVariable Integer idCurso) {

        Optional<CursosDocentesEntity> cursosDocentesEntity = cursosDocentesRepository.findById(idCurso);
        cursosDocentesEntity.ifPresent(docentesEntity -> cursosDocentesRepository.delete(docentesEntity));

    }

}
