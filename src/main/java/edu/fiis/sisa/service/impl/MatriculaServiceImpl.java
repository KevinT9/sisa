package edu.fiis.sisa.service.impl;

import edu.fiis.sisa.entities.CursosDocentesEntity;
import edu.fiis.sisa.entities.MatriculaEntity;
import edu.fiis.sisa.repository.MatriculaRepository;
import edu.fiis.sisa.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatriculaServiceImpl implements MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Override
    public boolean existeLaMatricula(MatriculaEntity matriculaEntity) {

        MatriculaEntity entity = matriculaRepository.findByUsuarioAndCursosDocentes(
                matriculaEntity.getUsuario(), matriculaEntity.getCursosDocentes()
        );

        return entity != null;
    }

    @Override
    public MatriculaEntity save(MatriculaEntity matriculaEntity) {
        return matriculaRepository.save(matriculaEntity);
    }

    @Override
    public Integer countByCursosDocentesAndCurrentYear(CursosDocentesEntity cursosDocentes) {
        return matriculaRepository.countByCursosDocentesAndCurrentYear(cursosDocentes);
    }


}
