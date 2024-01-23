package edu.fiis.sisa.controller.alumno;

import edu.fiis.sisa.entities.DocenteEntity;
import edu.fiis.sisa.entities.MatriculaEntity;
import edu.fiis.sisa.entities.UsuarioEntity;
import edu.fiis.sisa.repository.DocenteRepository;
import edu.fiis.sisa.repository.MatriculaRepository;
import edu.fiis.sisa.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Controller
public class DocentesController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DocenteRepository docenteRepository;
    @Autowired
    private MatriculaRepository matriculaRepository;

    @GetMapping("/alumno/docentes")
    String getCursosPage(Model model) {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        model.addAttribute("user", user);

//        List<DocenteEntity> listaDocentes = docenteRepository.findAll();
        List<DocenteEntity> listaDocentes = new ArrayList<>();

        List<MatriculaEntity> listaMatriculas = matriculaRepository.findAllByUsuario(user);
        for (MatriculaEntity matricula : listaMatriculas) {
            listaDocentes.add(matricula.getCursosDocentes().getDocente());
        }

        Set<DocenteEntity> setDocentes = new LinkedHashSet<>(listaDocentes);
        listaDocentes.clear();
        listaDocentes.addAll(setDocentes);

        model.addAttribute("listaDocentes", listaDocentes);
        return "alumno/docentes";
    }

}
