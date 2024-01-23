package edu.fiis.sisa.controller.docente;

import edu.fiis.sisa.entities.*;
import edu.fiis.sisa.repository.ClasesRepository;
import edu.fiis.sisa.repository.CursosDocentesRepository;
import edu.fiis.sisa.repository.CursosRepository;
import edu.fiis.sisa.repository.DocenteRepository;
import edu.fiis.sisa.service.UsuarioService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
public class ClasesController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private DocenteRepository docenteRepository;
    @Autowired
    private CursosRepository cursosRepository;
    @Autowired
    private CursosDocentesRepository cursosDocentesRepository;
    @Autowired
    private ClasesRepository clasesRepository;

    @GetMapping("/docente/clases")
    String getCursosPage(Model model) {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        model.addAttribute("user", user);

        DocenteEntity docente = docenteRepository.findByUsuario(user);
        model.addAttribute("idDocente", docente.getId());

        List<CursosDocentesEntity> listaCursosDocentes = cursosDocentesRepository.findAllByDocente(docente);
        List<CursosEntity> listaCursos = new ArrayList<>();

        for (CursosDocentesEntity cursoDoc : listaCursosDocentes){
            listaCursos.add(cursoDoc.getCurso());
        }


        List<ClasesEntity> listaClases = clasesRepository.findAllByDocente(docente);

        model.addAttribute("listaClases", listaClases);
        model.addAttribute("listaCursos", listaCursos);
        model.addAttribute("listaCursosDocentes", listaCursosDocentes);

        return "docente/clases";
    }

    @GetMapping("/docente/clases/{idCursoDocente}")
    String getCursosPorCurso(@PathVariable Integer idCursoDocente, Model model) {
        UsuarioEntity user = usuarioService.getAuthenticatedUsername();
        model.addAttribute("user", user);

        DocenteEntity docente = docenteRepository.findByUsuario(user);
        model.addAttribute("idDocente", docente.getId());

        List<CursosDocentesEntity> listaCursosDocentes = cursosDocentesRepository.findAllById(idCursoDocente);
        List<ClasesEntity> listaClases = clasesRepository.findByCursoDocente_Id(idCursoDocente);
        List<CursosEntity> listaCursos = new ArrayList<>();

        for (CursosDocentesEntity cursoDoc : listaCursosDocentes){
            listaCursos.add(cursoDoc.getCurso());
        }

        model.addAttribute("listaClases", listaClases);
        model.addAttribute("listaCursos", listaCursos);
        model.addAttribute("listaCursosDocentes", listaCursosDocentes);

        return "docente/clases";
    }

    @PostMapping("/crearClase")
    public String crearClase(@RequestParam("idCurso") Integer idCurso,
                             @RequestParam("idDelDocente") Integer idDelDocente,
//                             @RequestParam("fecha") String fecha,
                             @RequestParam("horaDeInicio") String horaDeInicio,
                             @RequestParam("horaDeFinalizacion") String horaDeFinalizacion,
                             @RequestParam("ubicacion") String ubicacion,
                             @RequestParam("descripcion") String descripcion,
                             @RequestParam("dia") String dia
    ) {

        ClasesEntity clase = new ClasesEntity();
        clase.setFecha(new Date(System.currentTimeMillis()));

        // Parsear la cadena a un objeto LocalTime
        LocalTime localTime = LocalTime.parse(horaDeInicio, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime localTime2 = LocalTime.parse(horaDeFinalizacion, DateTimeFormatter.ofPattern("HH:mm"));

        clase.setHoraDeInicio(Time.valueOf(localTime));
        clase.setHoraDeFinalizacion(Time.valueOf(localTime2));
        clase.setUbicacion(ubicacion);
        clase.setDescripcion(descripcion);
        clase.setDia(dia);

        Optional<CursosDocentesEntity> cursosDocentes = cursosDocentesRepository.findById(idCurso);

        if(cursosDocentes.isPresent()) {
            // Buscar el curso y el docente por sus IDs
            CursosEntity curso = cursosRepository.findById(cursosDocentes.get().getCurso().getId()).orElse(null);
            DocenteEntity docente = docenteRepository.findById(idDelDocente).orElse(null);

//        CursosDocentesEntity cursosDocentes = cursosDocentesRepository.findByCursoAndDocente(curso, docente);

            // Validar si se encontraron el curso y el docente
            if (curso != null && docente != null) {
                clase.setCursoDocente(cursosDocentes.get());
                clase.setCurso(curso);
                clase.setDocente(docente);

                clasesRepository.save(clase);
                log.info("Luego de guardar el ID: " + clase.getId());

                return "redirect:/docente/clases";
            }
        }



        return "redirect:/docente/clases";
    }

}
