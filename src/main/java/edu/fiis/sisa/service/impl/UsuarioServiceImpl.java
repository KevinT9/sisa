package edu.fiis.sisa.service.impl;

import edu.fiis.sisa.entities.UsuarioEntity;
import edu.fiis.sisa.repository.UsuarioRepository;
import edu.fiis.sisa.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void insert(UsuarioEntity usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // Encriptamos la contraseña antes de almacenarla

        usuarioRepository.save(usuario);
    }

    @Override
    public void update(UsuarioEntity usuario) {
        usuarioRepository.save(usuario);
    }

    @Override
    public List<UsuarioEntity> findAllByTipoDeUsuario(String tipoDeUsuario) {
        return usuarioRepository.findByTipoDeUsuario(tipoDeUsuario);
    }

    @Override
    public UsuarioEntity findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public UsuarioEntity getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getName() != null) {
            return usuarioRepository.findByEmail(authentication.getName());
        } else {
            return null;
        }
    }

    @Override
    public UsuarioEntity buscarEstudiantePorDni(String dni) {
        return usuarioRepository.findByDni(dni);
    }

}
