package edu.fiis.sisa.controller;

import edu.fiis.sisa.dto.UsuarioDTO;
import edu.fiis.sisa.entities.UsuarioEntity;
import edu.fiis.sisa.service.UsuarioService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class PerfilController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/perfil")
    String getPerfil(Model model) {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(user.getId());
        usuarioDTO.setNombre(user.getNombre());
        usuarioDTO.setApellido(user.getApellido());
        usuarioDTO.setTipoDeUsuario(user.getTipoDeUsuario());
        usuarioDTO.setEmail(user.getEmail());
        usuarioDTO.setPassword(user.getPassword());
        usuarioDTO.setFoto(user.getFoto());
        usuarioDTO.setCelular(user.getCelular());
        model.addAttribute("user", usuarioDTO);

        if (user.getTipoDeUsuario().equalsIgnoreCase("ADMIN")) {
            return "admin/perfil";
        } else if (user.getTipoDeUsuario().equalsIgnoreCase("DOCENTE")) {
            return "docente/perfil";
        } else if (user.getTipoDeUsuario().equalsIgnoreCase("ALUMNO")) {
            return "alumno/perfil";
        }

        return "perfil";
    }



    @PostMapping(value = "/perfil/actualizar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> actualizarInfo(@RequestPart("nombre") String nombre,
                                            @RequestPart("apellido") String apellido,
                                            @RequestPart("celular") String celular,
                                            @RequestPart("foto") MultipartFile foto) throws IOException {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();

        user.setNombre(nombre);
        user.setApellido(apellido);
        user.setCelular(celular);
        user.setFoto(Base64.encodeBase64String(foto.getBytes()));

        // Guarda la entidad en la base de datos
        usuarioService.update(user);

        return ResponseEntity.ok("usuario actualizdo");
    }

}
