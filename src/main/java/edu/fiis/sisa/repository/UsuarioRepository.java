package edu.fiis.sisa.repository;

import edu.fiis.sisa.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    UsuarioEntity findByEmail(String email);
    List<UsuarioEntity> findByTipoDeUsuario(String tipoDeUsuario);
    UsuarioEntity findByEmailAndPassword(String email, String password);

    UsuarioEntity findByDni(String dni);

}
