package edu.fiis.sisa.controller.alumno;

import edu.fiis.sisa.entities.*;
import edu.fiis.sisa.repository.CursosRepository;
import edu.fiis.sisa.repository.DocenteRepository;
import edu.fiis.sisa.repository.MatriculaRepository;
import edu.fiis.sisa.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CursosAlumnoController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CursosRepository cursosRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;


    @GetMapping("/alumno/cursos")
    String getCursosPage(Model model) {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        model.addAttribute("user", user);

        List<MatriculaEntity> listaMatricula = matriculaRepository.findAllByUsuario(user);
        List<CursosDocentesEntity> listaCursosDocentes = new ArrayList<>();

        for (MatriculaEntity matricula : listaMatricula) {
            listaCursosDocentes.add(matricula.getCursosDocentes());
        }

        model.addAttribute("listaCursosDocentes", listaCursosDocentes);

        return "alumno/cursos";
    }

}
