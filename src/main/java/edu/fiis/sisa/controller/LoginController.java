package edu.fiis.sisa.controller;

import edu.fiis.sisa.entities.UsuarioEntity;
import edu.fiis.sisa.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/")
    String index(Model model) {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        model.addAttribute("user", user);

        if (user.getTipoDeUsuario().equalsIgnoreCase("ADMIN")) {
            return "admin/index";
        } else if (user.getTipoDeUsuario().equalsIgnoreCase("ALUMNO")) {
            return "alumno/index";
        } else if (user.getTipoDeUsuario().equalsIgnoreCase("DOCENTE")) {
            return "docente/index";
        }

        return "index";
    }

    @GetMapping("/alumno")
    String indexAlumno(Model model) {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        model.addAttribute("user", user);
        if (user.getTipoDeUsuario().equalsIgnoreCase("ALUMNO") || user.getTipoDeUsuario().equalsIgnoreCase("ADMIN")) {
            return "alumno/index";
        } else {
            return "index";
        }
    }

    @GetMapping("/docente")
    String indexDocente(Model model) {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        model.addAttribute("user", user);
        if (user.getTipoDeUsuario().equalsIgnoreCase("DOCENTE") || user.getTipoDeUsuario().equalsIgnoreCase("ADMIN")) {
            return "docente/index";
        } else {
            return "index";
        }
    }

    @GetMapping("/admin")
    String indexAdmin(Model model) {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        model.addAttribute("user", user);
        if (user.getTipoDeUsuario().equalsIgnoreCase("ADMIN")) {
            return "admin/index";
        } else {
            return "index";
        }
    }

    @GetMapping("/index")
    String index2(Model model) {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        model.addAttribute("user", user);
        return "index";
    }

    @PostMapping("/registrarse")
    ResponseEntity<String> registrarse(@ModelAttribute UsuarioEntity usuario) {


        String filePath = "static/image/perfil/";

        // Verificar si genero es null antes de hacer la comparación
        if (usuario.getGenero() != null) {
            if (usuario.getGenero().equalsIgnoreCase("masculino")) {
                filePath += "perfil_hombre.png";
            } else if (usuario.getGenero().equalsIgnoreCase("femenino")) {
                filePath += "perfil_mujer.png";
            } else {
                filePath += "perfil_sin_genero.png";
            }

            Resource resource = new ClassPathResource(filePath);

            if (resource.exists()) {
                try {
                    byte[] imageBytes = StreamUtils.copyToByteArray(resource.getInputStream());
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);

                    usuario.setFoto(base64Image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        usuario.setFechaRegistro(new Date());
        usuario.setEstado(true);
        usuario.setTipoDeUsuario("ALUMNO");
        usuarioService.insert(usuario);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/home")
    String home(Model model) {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        model.addAttribute("user", user);
        return "home";
    }

    @GetMapping("/sidebars")
    String index3(Model model) {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        model.addAttribute("user", user);
        return "sidebars";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied"; // Nombre de la página HTML personalizada
    }

}
