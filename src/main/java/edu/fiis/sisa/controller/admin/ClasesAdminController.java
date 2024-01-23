package edu.fiis.sisa.controller.admin;

import edu.fiis.sisa.entities.UsuarioEntity;
import edu.fiis.sisa.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClasesAdminController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/admin/clases")
    String getClasesAdmin(Model model) {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        model.addAttribute("user", user);

        return "admin/clases";
    }
}
