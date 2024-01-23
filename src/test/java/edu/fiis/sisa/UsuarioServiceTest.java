package edu.fiis.sisa;

import edu.fiis.sisa.entities.UsuarioEntity;
import edu.fiis.sisa.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService; // Debes inyectar el servicio que interactúa con la base de datos

    @Autowired
    private PasswordEncoder passwordEncoder; // Inyecta el encoder de contraseñas

//    @Test
//    public void testAgregarUsuario() {
//        UsuarioEntity usuario = new UsuarioEntity();
//        usuario.setNombre("Kevin");
//        usuario.setApellido("Tarazona");
//        usuario.setGenero("masculino");
//        usuario.setFechaRegistro(new Date());
//        usuario.setEstado(true);
//        usuario.setTipoDeUsuario("ADMIN");
//        usuario.setEmail("1234523@gmail.com");
//        String password = "123"; // Contraseña sin encriptar
//        usuario.setPassword(passwordEncoder.encode(password)); // Encriptamos la contraseña antes de almacenarla
//
//        usuarioService.insert(usuario); // Utiliza el método de tu servicio para agregar el usuario
//
//        // Realiza aserciones para verificar que el usuario se ha añadido correctamente
//        UsuarioEntity usuarioGuardado = usuarioService.findByEmail("kevintf99@gmail.com");
//        assertNotNull(usuarioGuardado);
//        assertEquals("Kevin", usuarioGuardado.getNombre());
//        assertEquals("Tarazona", usuarioGuardado.getApellido());
//        // Asegúrate de que la contraseña en la base de datos esté encriptada
//        assertTrue(passwordEncoder.matches(password, usuarioGuardado.getPassword()));
//    }
}
