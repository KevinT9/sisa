package edu.fiis.sisa.service;

import edu.fiis.sisa.entities.UsuarioEntity;

import java.util.List;

public interface UsuarioService {
    void insert(UsuarioEntity usuario);
    void update(UsuarioEntity usuario);
    List<UsuarioEntity> findAllByTipoDeUsuario(String tipoDeUsuario);
    UsuarioEntity findByEmail(String email);
    UsuarioEntity getAuthenticatedUsername();

    UsuarioEntity buscarEstudiantePorDni(String dni);

}
