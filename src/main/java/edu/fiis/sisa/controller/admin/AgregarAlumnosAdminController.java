package edu.fiis.sisa.controller.admin;

import edu.fiis.sisa.entities.CursosDocentesEntity;
import edu.fiis.sisa.entities.UsuarioEntity;
import edu.fiis.sisa.repository.CursosDocentesRepository;
import edu.fiis.sisa.service.UsuarioService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
@Log4j2
public class AgregarAlumnosAdminController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CursosDocentesRepository cursosDocentesRepository;

    @GetMapping("/admin/matricula")
    String getAgregarPage(Model model) {

        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        model.addAttribute("user", user);

        List<CursosDocentesEntity> listaCursosDocentes = cursosDocentesRepository.findAll();

        List<String> listaCiclos = new ArrayList<>();

        for (CursosDocentesEntity cursoDocente : listaCursosDocentes) {
            listaCiclos.add(cursoDocente.getCurso().getCiclo());
        }

        // Eliminar duplicados convirtiendo la lista a un Set
        HashSet<String> set = new HashSet<>(listaCiclos);
        listaCiclos.clear();

        // Agregar elementos únicos de nuevo a la lista
        listaCiclos.addAll(set);

        model.addAttribute("listaCiclos", listaCiclos);
        model.addAttribute("listaCursosDocentes", listaCursosDocentes);

        return "admin/matricula";
    }



}
