package edu.fiis.sisa.controller.alumno;

import edu.fiis.sisa.entities.AsistenciaEntity;
import edu.fiis.sisa.entities.ClasesEntity;
import edu.fiis.sisa.entities.CursosEntity;
import edu.fiis.sisa.entities.UsuarioEntity;
import edu.fiis.sisa.repository.AsistenciaRepository;
import edu.fiis.sisa.repository.ClasesRepository;
import edu.fiis.sisa.repository.CursosRepository;
import edu.fiis.sisa.repository.MatriculaRepository;
import edu.fiis.sisa.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class AsistenciaAlumnoController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private CursosRepository cursosRepository;
    @Autowired
    private AsistenciaRepository asistenciaRepository;
    @Autowired
    private ClasesRepository clasesRepository;

    @GetMapping("/alumno/asistencia")
    String getAsistencia(Model model) {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        model.addAttribute("user", user);

        List<UsuarioEntity> listaUsuarios = usuarioService.findAllByTipoDeUsuario("ALUMNO");
        model.addAttribute("listaUsuarios", listaUsuarios);

        CursosEntity curso = new CursosEntity();
        curso.setNombreDelCurso("Gestion de proyectos");

        model.addAttribute("curso", curso);
        return "alumno/asistencia";
    }

    @GetMapping("/alumno/asistencia/{idClase}")
    String getAsistenciaCurso(@PathVariable Integer idClase, Model model) {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        model.addAttribute("user", user);

        Optional<ClasesEntity> clase = clasesRepository.findById(idClase);

        if (clase.isPresent()) {
            List<AsistenciaEntity> listaAsistencia = asistenciaRepository.findAllByClaseAndUsuario(clase.get(), user);
            if (listaAsistencia.isEmpty()) {
                model.addAttribute("clase", clase.get());
                return "alumno/asistenciaVacio";
            }
            model.addAttribute("listaAsistencia", listaAsistencia);
            return "alumno/asistencia";
        }

        model.addAttribute("error", "Curso no encontrado");
        return "error/404";
    }
}
