package edu.fiis.sisa.controller.admin;

import edu.fiis.sisa.dto.CursoDTO;
import edu.fiis.sisa.entities.CursosEntity;
import edu.fiis.sisa.entities.DocenteEntity;
import edu.fiis.sisa.entities.UsuarioEntity;
import edu.fiis.sisa.repository.CursosRepository;
import edu.fiis.sisa.repository.DocenteRepository;
import edu.fiis.sisa.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CursosAdminController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CursosRepository cursosRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @GetMapping("/admin/cursos")
    String getCursosAdmin(Model model) {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        model.addAttribute("user", user);

        List<CursosEntity> listaCursos = cursosRepository.findAll();
        model.addAttribute("listaCursos", listaCursos);

        List<DocenteEntity> listaDocentes = docenteRepository.findAll();
        model.addAttribute("listaDocentes", listaDocentes);

        return "admin/cursos";
    }

}
