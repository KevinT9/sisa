package edu.fiis.sisa.service.impl;

import edu.fiis.sisa.entities.DocenteEntity;
import edu.fiis.sisa.entities.UsuarioEntity;
import edu.fiis.sisa.repository.DocenteRepository;
import edu.fiis.sisa.repository.UsuarioRepository;
import edu.fiis.sisa.service.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DocenteServiceImpl implements DocenteService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public DocenteEntity saveDocente(DocenteEntity docenteEntity) {

        UsuarioEntity usuario = docenteEntity.getUsuario();

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        usuario = usuarioRepository.save(usuario);

        docenteEntity.setUsuario(usuario);

        return docenteRepository.save(docenteEntity);

    }
}
