package edu.fiis.sisa.rest;

import edu.fiis.sisa.dto.UsuarioAsistenciaDTO;
import edu.fiis.sisa.entities.*;
import edu.fiis.sisa.repository.AsistenciaRepository;
import edu.fiis.sisa.repository.ClasesRepository;
import edu.fiis.sisa.repository.CursosDocentesRepository;
import edu.fiis.sisa.repository.MatriculaRepository;
import edu.fiis.sisa.service.MatriculaService;
import edu.fiis.sisa.service.UsuarioService;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alumno")
@Log4j2
public class AlumnoRestController {

    private final UsuarioService usuarioService;
    private final CursosDocentesRepository cursosDocentesRepository;
    private final MatriculaService matriculaService;
    private final ClasesRepository clasesRepository;
    private final AsistenciaRepository asistenciaRepository;

    public AlumnoRestController(UsuarioService usuarioService, CursosDocentesRepository cursosDocentesRepository,
                                MatriculaService matriculaService, ClasesRepository clasesRepository,
                                AsistenciaRepository asistenciaRepository) {
        this.usuarioService = usuarioService;
        this.cursosDocentesRepository = cursosDocentesRepository;
        this.matriculaService = matriculaService;
        this.clasesRepository = clasesRepository;
        this.asistenciaRepository = asistenciaRepository;
    }

    @GetMapping("/buscarEstudiantePorDNI")
    public UsuarioEntity buscarPorDNI(@RequestParam String dni) {
        return usuarioService.buscarEstudiantePorDni(dni);
    }

    @PostMapping("/guardarMatricula")
    public ResponseEntity<String> guardarMatricula(HttpServletRequest request) {

        Integer idCurso = Integer.parseInt(request.getParameter("idCurso"));
        String dni = request.getParameter("dni");

        UsuarioEntity alumno = usuarioService.buscarEstudiantePorDni(dni);
        Optional<CursosDocentesEntity> cursosDocentes = cursosDocentesRepository.findById(idCurso);

        MatriculaEntity matriculaEntity = new MatriculaEntity();
        if (cursosDocentes.isPresent()) {
            Integer maxAlumnos = cursosDocentes.get().getCurso().getMaxAlumnos();
            Integer matriculados = matriculaService.countByCursosDocentesAndCurrentYear(cursosDocentes.get());

            if (matriculados >= maxAlumnos) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Se alcanzó el máximo de alumnos para el curso");
            }

            matriculaEntity.setEncargadoMatricula(usuarioService.getAuthenticatedUsername().getNombre());
            matriculaEntity.setUsuario(alumno);
            matriculaEntity.setFechaRegistro(new Time(System.currentTimeMillis()));
            matriculaEntity.setTimestamp(LocalDateTime.now());
            matriculaEntity.setCursosDocentes(cursosDocentes.get());

            if (matriculaService.existeLaMatricula(matriculaEntity)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("La matrícula ya existe");
            } else {
                matriculaService.save(matriculaEntity);
                return ResponseEntity.ok("Matrícula guardada correctamente");
            }
        }

        return ResponseEntity.badRequest().body("Error al procesar la solicitud");
    }

    @PostMapping("/guardarAsistencia")
    public void recibirDatos(
            @RequestBody List<UsuarioAsistenciaDTO> datosUsuarios,
            @RequestHeader(value = "Descargar-Reporte", required = false) String descargarReporteHeader,
            HttpServletResponse response
    ) {
        Optional<ClasesEntity> clasesEntity = clasesRepository.findById(datosUsuarios.get(0).getIdClase());

        if (clasesEntity.isPresent()) {
            LocalDateTime fechaActual = LocalDateTime.now();
            List<AsistenciaEntity> listaAsistencia = asistenciaRepository.findByFechaAndClase(fechaActual, datosUsuarios.get(0).getIdClase());

            if (!listaAsistencia.isEmpty()) {

                for (UsuarioAsistenciaDTO usuario : datosUsuarios) {
                    UsuarioEntity usuarioBuscado = usuarioService.findByEmail(usuario.getEmail());

                    for (AsistenciaEntity asistencia : listaAsistencia) {
                        if (asistencia.getUsuario() == usuarioBuscado) {
                            asistencia.setUsuario(usuarioBuscado);
                            asistencia.setClase(clasesEntity.get());
                            asistencia.setFechaDeAsistencia(fechaActual);
                            asistencia.setPresente(usuario.isPresente());
                            asistencia.setTardanza(usuario.isTardanza());
                            asistencia.setFalta(usuario.isFalta());
                            asistenciaRepository.save(asistencia);
                        }
                    }
                }
            } else {
                LocalDateTime fechaYHoraActual = LocalDateTime.now();

                for (UsuarioAsistenciaDTO usuario : datosUsuarios) {
                    UsuarioEntity usuarioBuscado = usuarioService.findByEmail(usuario.getEmail());

                    if (usuarioBuscado != null) {
                        AsistenciaEntity asistencia = new AsistenciaEntity();
                        asistencia.setUsuario(usuarioBuscado);
                        asistencia.setClase(clasesEntity.get());
                        asistencia.setFechaDeAsistencia(fechaYHoraActual);
                        asistencia.setPresente(usuario.isPresente());
                        asistencia.setTardanza(usuario.isTardanza());
                        asistencia.setFalta(usuario.isFalta());

                        asistencia = asistenciaRepository.save(asistencia);
                        listaAsistencia.add(asistencia);
                    }
                }
            }

            if (descargarReporteHeader != null && !descargarReporteHeader.isEmpty()) {
                boolean descargarReporte = Boolean.parseBoolean(descargarReporteHeader);

                if (descargarReporte) {
                    generarExcel(listaAsistencia, response);
                }
            }
        }


    }


    public void generarExcel(List<AsistenciaEntity> listaAsistencia, HttpServletResponse response) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Asistencias");

            // Crear encabezados
            Row headerRow = sheet.createRow(0);
//            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(0).setCellValue("Alumno");
            headerRow.createCell(1).setCellValue("Clase");
            headerRow.createCell(2).setCellValue("Fecha de Asistencia");
            headerRow.createCell(3).setCellValue("Presente");
            headerRow.createCell(4).setCellValue("Tardanza");
            headerRow.createCell(5).setCellValue("Falta");

            // Formato para la fecha
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            int rowNum = 1;
            for (AsistenciaEntity asistencia : listaAsistencia) {
                Row row = sheet.createRow(rowNum++);
//                row.createCell(0).setCellValue(asistencia.getId());
                row.createCell(0).setCellValue(asistencia.getUsuario().getNombre() + " " + asistencia.getUsuario().getApellido());
                row.createCell(1).setCellValue(asistencia.getClase().getCurso().getNombreDelCurso() + " - " + asistencia.getClase().getCursoDocente().getSeccion());
                row.createCell(2).setCellValue(asistencia.getFechaDeAsistencia().format(formatter));
                row.createCell(3).setCellValue(asistencia.getPresente());
                row.createCell(4).setCellValue(asistencia.getTardanza());
                row.createCell(5).setCellValue(asistencia.getFalta());
            }

            // Configurar la respuesta HTTP para devolver el archivo Excel
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=asistencias.xlsx");

            // Escribir el archivo Excel en la respuesta
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            // Manejo de errores
            log.error("Error: " + e.getMessage(), e);
        }
    }

}
