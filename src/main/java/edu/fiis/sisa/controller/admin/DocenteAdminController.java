package edu.fiis.sisa.controller.admin;

import edu.fiis.sisa.entities.DocenteEntity;
import edu.fiis.sisa.entities.UsuarioEntity;
import edu.fiis.sisa.repository.DocenteRepository;
import edu.fiis.sisa.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DocenteAdminController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DocenteRepository docenteRepository;

    @GetMapping("/admin/docente")
    String getDocente(Model model) {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        model.addAttribute("user", user);

        List<DocenteEntity> listaDocentes = docenteRepository.findAll();
        model.addAttribute("listaDocentes", listaDocentes);


        return "admin/docente";
    }



}
