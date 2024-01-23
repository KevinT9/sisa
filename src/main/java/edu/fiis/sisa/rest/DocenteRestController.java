package edu.fiis.sisa.rest;

import edu.fiis.sisa.entities.DocenteEntity;
import edu.fiis.sisa.entities.UsuarioEntity;
import edu.fiis.sisa.repository.DocenteRepository;
import edu.fiis.sisa.repository.UsuarioRepository;
import edu.fiis.sisa.service.DocenteService;
import edu.fiis.sisa.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/docente")
public class DocenteRestController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private DocenteService docenteService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public ResponseEntity<List<DocenteEntity>> getDocentes(){
        return ResponseEntity.ok(docenteRepository.findAll());
    }

    @GetMapping("/{idDocente}")
    public ResponseEntity<Optional<DocenteEntity>> getDocente(@PathVariable Integer idDocente){
        return ResponseEntity.ok(docenteRepository.findById(idDocente));
    }

    @PostMapping("/")
    public ResponseEntity<String> agregarDocente(
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("tipoUsuario") String tipoUsuario,
            @RequestParam("email") String email,
            @RequestParam("celular") String celular,
            @RequestParam("especializacion") String especializacion,
            @RequestParam("foto") MultipartFile foto
    ) {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setTipoDeUsuario(tipoUsuario);
        usuario.setEmail(email);
        usuario.setCelular(celular);
        usuario.setPassword(passwordEncoder.encode("123"));

        DocenteEntity docente = new DocenteEntity();
        docente.setEspecializacion(especializacion);

        try {
            String fotoBase64 = Base64.getEncoder().encodeToString(foto.getBytes());
            usuario.setFoto(fotoBase64);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la imagen");
        }

        usuario = usuarioRepository.save(usuario);

        docente.setUsuario(usuario);
        docenteRepository.save(docente);

        return ResponseEntity.ok("Docente agregado exitosamente");
    }

    @PutMapping("/")
    public ResponseEntity<DocenteEntity> updateDocente(@RequestBody DocenteEntity docenteEntity){
        return ResponseEntity.ok(docenteRepository.save(docenteEntity));
    }

    @DeleteMapping("/{idDocente}")
    public ResponseEntity<String> deleteDocente(@PathVariable Integer idDocente) {
        if (docenteRepository.existsById(idDocente)) {
            // Elimina el docente por ID
            docenteRepository.deleteById(idDocente);
            return new ResponseEntity<>("Docente eliminado exitosamente", HttpStatus.OK);
        } else {
            // Si el docente no existe, devuelve un mensaje de error
            return new ResponseEntity<>("No se encontró el docente con ID: " + idDocente, HttpStatus.NOT_FOUND);
        }
    }
}
