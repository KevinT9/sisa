package edu.fiis.sisa.repository;

import edu.fiis.sisa.entities.AsistenciaEntity;
import edu.fiis.sisa.entities.ClasesEntity;
import edu.fiis.sisa.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AsistenciaRepository extends JpaRepository<AsistenciaEntity, Integer> {

    List<AsistenciaEntity> findAllByClaseAndUsuario(ClasesEntity clases, UsuarioEntity usuario);


    @Query("SELECT a FROM AsistenciaEntity a WHERE a.fechaDeAsistencia = :fecha")
    List<AsistenciaEntity> findByFechaDeAsistencia(@Param("fecha") LocalDateTime fecha);

    @Query("SELECT a FROM AsistenciaEntity a WHERE a.fechaDeAsistencia = :fecha AND a.usuario = :usuario")
    List<AsistenciaEntity> findByFechaYUsuario(
            @Param("fecha") LocalDateTime fecha,
            @Param("usuario") UsuarioEntity usuario
    );

    @Query("SELECT a FROM AsistenciaEntity a WHERE DATE(a.fechaDeAsistencia) = DATE(:fecha) AND a.clase.id = :claseId")
    List<AsistenciaEntity> findByFechaAndClase(@Param("fecha") LocalDateTime fecha, @Param("claseId") Integer claseId);
}
