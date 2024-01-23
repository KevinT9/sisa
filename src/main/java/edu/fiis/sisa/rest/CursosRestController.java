package edu.fiis.sisa.rest;

import edu.fiis.sisa.entities.CursosEntity;
import edu.fiis.sisa.repository.CursosRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@Log4j2
public class CursosRestController {

    @Autowired
    private CursosRepository cursosRepository;

    @GetMapping("/")
    public ResponseEntity<List<CursosEntity>> getCursos() {
        log.info("Getting cursos");
        return ResponseEntity.ok(cursosRepository.findAll());
    }

    @PostMapping(value = "/")
    public ResponseEntity<String> saveCursosEntity(
            @RequestParam("nombreDelCurso") String nombreDelCurso,
            @RequestParam("codigo") String codigo,
            @RequestParam("ciclo") String ciclo,
            @RequestParam("maxAlumnos") Integer maxAlumnos,
            @RequestParam("minAlumnos") Integer minAlumnos,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("planEstudios") Integer planEstudios,
            @RequestParam("foto") MultipartFile foto
    ) {

        CursosEntity cursosEntity = new CursosEntity();
        cursosEntity.setNombreDelCurso(nombreDelCurso);
        cursosEntity.setCodigo(codigo);
        cursosEntity.setCiclo(ciclo);
        cursosEntity.setMaxAlumnos(maxAlumnos);
        cursosEntity.setMinAlumnos(minAlumnos);
        cursosEntity.setDescripcion(descripcion);
        cursosEntity.setPlanEstudios(planEstudios + "");

        try {
            String fotoBase64 = Base64.getEncoder().encodeToString(foto.getBytes());
            cursosEntity.setFotoBase64(fotoBase64);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        cursosEntity = cursosRepository.save(cursosEntity);
        log.info("Curso añadido: " + cursosEntity.getId() + " - " + cursosEntity.getNombreDelCurso());
        return ResponseEntity.ok().body("Curso añadido: " + cursosEntity.getId() + " - " + cursosEntity.getNombreDelCurso());
    }

    @DeleteMapping("/{idCurso}")
    public ResponseEntity<String> deleteDocente(@PathVariable Integer idCurso) {
        if (cursosRepository.existsById(idCurso)) {
            // Elimina el curso por ID
            cursosRepository.deleteById(idCurso);
            log.info("Curso con ID: " + idCurso + " eliminado con éxito");
            return new ResponseEntity<>("Curso eliminado exitosamente", HttpStatus.OK);
        } else {
            // Si el curso no existe, devuelve un mensaje de error
            log.info("No se encontro el curso con ID: " + idCurso);
            return new ResponseEntity<>("No se encontró el docente con ID: " + idCurso, HttpStatus.NOT_FOUND);
        }
    }

}
