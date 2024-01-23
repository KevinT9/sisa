package edu.fiis.sisa.repository;

import edu.fiis.sisa.entities.CursosDocentesEntity;
import edu.fiis.sisa.entities.MatriculaEntity;
import edu.fiis.sisa.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<MatriculaEntity, Integer> {

    MatriculaEntity findByUsuarioAndCursosDocentes(UsuarioEntity usuario, CursosDocentesEntity cursosDocentes);

    List<MatriculaEntity> findAllByCursosDocentes(CursosDocentesEntity cursosDocentes);

    List<MatriculaEntity> findAllByUsuario(UsuarioEntity usuario);

    @Query("SELECT COUNT(m) FROM MatriculaEntity m WHERE m.cursosDocentes = :cursosDocentes AND YEAR(m.timestamp) = YEAR(CURRENT_TIMESTAMP)")
    Integer countByCursosDocentesAndCurrentYear(CursosDocentesEntity cursosDocentes);
}
