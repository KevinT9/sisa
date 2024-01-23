package edu.fiis.sisa.controller.docente;

import edu.fiis.sisa.dto.UsuarioAsistenciaDTO;
import edu.fiis.sisa.entities.*;
import edu.fiis.sisa.repository.AsistenciaRepository;
import edu.fiis.sisa.repository.ClasesRepository;
import edu.fiis.sisa.repository.CursosRepository;
import edu.fiis.sisa.repository.MatriculaRepository;
import edu.fiis.sisa.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AsistenciaController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private CursosRepository cursosRepository;
    @Autowired
    private ClasesRepository clasesRepository;
    @Autowired
    private MatriculaRepository matriculaRepository;
    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @GetMapping("/docente/asistencia")
    String getAsistencia(Model model) {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        model.addAttribute("user", user);

        List<UsuarioEntity> listaUsuarios = usuarioService.findAllByTipoDeUsuario("ALUMNO");
        model.addAttribute("listaUsuarios", listaUsuarios);

        CursosEntity curso = new CursosEntity();
        curso.setNombreDelCurso("Gestion de proyectos");

        model.addAttribute("curso", curso);
        return "docente/asistencia";
    }

    @GetMapping("/docente/asistencia/{nombreCurso}")
    String getAsistenciaCurso(@PathVariable String nombreCurso, Model model) {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        model.addAttribute("user", user);

        List<UsuarioEntity> listaUsuarios = usuarioService.findAllByTipoDeUsuario("ALUMNO");
        model.addAttribute("listaUsuarios", listaUsuarios);

        CursosEntity curso = cursosRepository.findByNombreDelCurso(nombreCurso);

        if (curso != null) {
            model.addAttribute("curso", curso);
        } else {
            model.addAttribute("error", "Curso no encontrado");
            return "error/404";
        }

        return "docente/asistencia";
    }
    @GetMapping("/docente/registrarAsistencia")
    String getAsistenciaDocente(@RequestParam Integer idClase, Model model) {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        model.addAttribute("user", user);

        Optional<ClasesEntity> clasesEntity = clasesRepository.findById(idClase);

        if(clasesEntity.isPresent()) {

            LocalDateTime fechaActual = LocalDateTime.now();
            List<AsistenciaEntity> listaAsistencias = asistenciaRepository.findByFechaAndClase(fechaActual, idClase);

            if(!listaAsistencias.isEmpty()) {
                model.addAttribute("listaAsistencias", listaAsistencias);
                return "docente/asistenciaActualizar";
            }

            List<MatriculaEntity> listaMatriculados = matriculaRepository.findAllByCursosDocentes(clasesEntity.get().getCursoDocente());
            List<UsuarioEntity> listaUsuarios = new ArrayList<>();

            for (MatriculaEntity matricula : listaMatriculados) {
                listaUsuarios.add(matricula.getUsuario());
            }

            model.addAttribute("listaUsuarios", listaUsuarios);
            model.addAttribute("clase", clasesEntity.get());

            if (clasesEntity.get().getCurso() != null) {
                model.addAttribute("curso", clasesEntity.get().getCurso());
            } else {
                model.addAttribute("error", "Curso no encontrado");
                return "error/404";
            }
        }

        return "docente/asistencia";
    }
//    @PostMapping("/docente/guardarAsistencia")
//    String saveAsistencia(@RequestParam Integer idClase, Model model) {
//        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
//        model.addAttribute("user", user);
//
//        Optional<ClasesEntity> clasesEntity = clasesRepository.findById(idClase);
//
//        if(clasesEntity.isPresent()) {
//
//            List<MatriculaEntity> listaMatriculados = matriculaRepository.findAllByCursosDocentes(clasesEntity.get().getCursoDocente());
//            List<UsuarioEntity> listaUsuarios = new ArrayList<>();
//
//            for (MatriculaEntity matricula : listaMatriculados) {
//                listaUsuarios.add(matricula.getUsuario());
//            }
//
//            model.addAttribute("listaUsuarios", listaUsuarios);
//            model.addAttribute("clase", clasesEntity.get());
//
//            if (clasesEntity.get().getCurso() != null) {
//                model.addAttribute("curso", clasesEntity.get().getCurso());
//            } else {
//                model.addAttribute("error", "Curso no encontrado");
//                return "error/404";
//            }
//        }
//
//        return "docente/asistencia";
//    }


}
