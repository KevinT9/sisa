package edu.fiis.sisa.controller.alumno;

import edu.fiis.sisa.entities.ClasesEntity;
import edu.fiis.sisa.entities.MatriculaEntity;
import edu.fiis.sisa.entities.UsuarioEntity;
import edu.fiis.sisa.repository.ClasesRepository;
import edu.fiis.sisa.repository.CursosRepository;
import edu.fiis.sisa.repository.MatriculaRepository;
import edu.fiis.sisa.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ClasesAlumnoController {


    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CursosRepository cursosRepository;
    @Autowired
    private ClasesRepository clasesRepository;
    @Autowired
    private MatriculaRepository matriculaRepository;

    @GetMapping("/alumno/clases")
    String getCursosPage(Model model) {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        model.addAttribute("user", user);

        List<MatriculaEntity> listaMatricula = matriculaRepository.findAllByUsuario(user);
        List<List<ClasesEntity>> listasDeClases = new ArrayList<>();
        for (MatriculaEntity matricula : listaMatricula) {
            List<ClasesEntity> listaClasesPorCursoDocente = clasesRepository.findByCursoDocente_Id(matricula.getCursosDocentes().getId());
            listasDeClases.add(listaClasesPorCursoDocente);
        }

        List<ClasesEntity> consolidado = new ArrayList<>();
        for (List<ClasesEntity> clases : listasDeClases) {
            consolidado.addAll(clases);
        }

        model.addAttribute("listasDeClases", listasDeClases);
        model.addAttribute("consolidado", consolidado);

        return "alumno/clases";
    }

    @GetMapping("/alumno/clases/{idCursoDocente}")
    String getCursosPage(@PathVariable Integer idCursoDocente, Model model) {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        model.addAttribute("user", user);

//        List<MatriculaEntity> listaMatricula = matriculaRepository.findAllByUsuario(user);
        List<List<ClasesEntity>> listasDeClases = new ArrayList<>();
        List<ClasesEntity> listaClasesPorCursoDocente = clasesRepository.findByCursoDocente_Id(idCursoDocente);
        listasDeClases.add(listaClasesPorCursoDocente);
//        for (MatriculaEntity matricula : listaMatricula) {
//        }

        List<ClasesEntity> consolidado = new ArrayList<>();
        for (List<ClasesEntity> clases : listasDeClases) {
            consolidado.addAll(clases);
        }

        model.addAttribute("listasDeClases", listasDeClases);
        model.addAttribute("consolidado", consolidado);

        return "alumno/clases";
    }

}
