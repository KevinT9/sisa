package edu.fiis.sisa.repository;

import edu.fiis.sisa.entities.DocenteEntity;
import edu.fiis.sisa.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocenteRepository extends JpaRepository<DocenteEntity, Integer> {
    Optional<DocenteEntity> findById(Integer id);
    DocenteEntity findByUsuario(UsuarioEntity usuario);


}
