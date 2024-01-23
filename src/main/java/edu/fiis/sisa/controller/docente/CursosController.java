package edu.fiis.sisa.controller.docente;

import edu.fiis.sisa.entities.CursosDocentesEntity;
import edu.fiis.sisa.entities.CursosEntity;
import edu.fiis.sisa.entities.DocenteEntity;
import edu.fiis.sisa.entities.UsuarioEntity;
import edu.fiis.sisa.repository.CursosDocentesRepository;
import edu.fiis.sisa.repository.CursosRepository;
import edu.fiis.sisa.repository.DocenteRepository;
import edu.fiis.sisa.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CursosController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private CursosDocentesRepository cursosDocentesRepository;


    @GetMapping("/docente/cursos")
    String getCursosPage(Model model) {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        model.addAttribute("user", user);

        DocenteEntity docente = docenteRepository.findByUsuario(user);

        List<CursosDocentesEntity> listaCursosDocentes = cursosDocentesRepository.findAllByDocente(docente);

        List<CursosEntity> listaCursos = new ArrayList<>();

        for (CursosDocentesEntity cursoDocente : listaCursosDocentes) {
            listaCursos.add(cursoDocente.getCurso());
        }

        model.addAttribute("listaCursos", listaCursos);
        model.addAttribute("listaCursosDocentes", listaCursosDocentes);

        return "docente/cursos";
    }

    @GetMapping("/docente/agregarAlumnos")
    String getAgregarAlumnos(Model model) {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        model.addAttribute("user", user);

        DocenteEntity docente = docenteRepository.findByUsuario(user);

        List<CursosDocentesEntity> listaCursosDocentes = cursosDocentesRepository.findAllByDocente(docente);

        List<CursosEntity> listaCursos = new ArrayList<>();
        for (CursosDocentesEntity cursoDocente : listaCursosDocentes) {
            listaCursos.add(cursoDocente.getCurso());
        }

        model.addAttribute("listaCursos", listaCursos);
        model.addAttribute("listaCursosDocentes", listaCursosDocentes);

        return "docente/agregarAlumnos";
    }


}
