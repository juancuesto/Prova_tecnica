package com.provaTecnica.provaTecnica_activitats.controler;

import com.provaTecnica.provaTecnica_activitats.entities.Actividad;
import com.provaTecnica.provaTecnica_activitats.service.ActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appActivitats")
public class ActividadController {

    @Autowired
    private ActividadService actividadService;

    // Crear una nueva actividad
    @PostMapping("/activitats")
    public ResponseEntity<String> crearActividad(@RequestBody Actividad actividad) {
        try {
            String mensaje = actividadService.crearActividad(actividad);
            return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error inesperado.");
        }
    }

    // Consultar una actividad por ID
    @GetMapping("/activitats/{id}")
    public ResponseEntity<?> consultarActividadById(@PathVariable Long id) {
        try {
            Actividad actividad = actividadService.consultarActividadPorId(id);
            if (actividad == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró una actividad con el ID especificado.");
            }
            return ResponseEntity.ok(actividad);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error inesperado.");
        }
    }

    // Apuntarse a una actividad
    @PostMapping("/activitats/apuntarse/{usuarioId}/{actividadId}")
    public ResponseEntity<String> apuntarseActividad(@PathVariable Long usuarioId, @PathVariable Long actividadId) {
        try {
            String mensaje = actividadService.apuntarseActividad(usuarioId, actividadId);
            return ResponseEntity.ok(mensaje);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error inesperado.");
        }
    }

    // Importar actividades desde una lista
    @PostMapping("/activitats/importar")
    public ResponseEntity<String> importarActividades(@RequestBody List<Actividad> actividades) {
        try {
            String mensaje = actividadService.importarActividades(actividades);
            return ResponseEntity.ok(mensaje);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error inesperado.");
        }
    }

    // Exportar todas las actividades como una lista
    @GetMapping("/activitats/exportar")
    public ResponseEntity<?> exportarActividades() {
        try {
            List<Actividad> actividades = actividadService.exportarActividades();
            if (actividades.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay actividades disponibles para exportar.");
            }
            return ResponseEntity.ok(actividades);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error inesperado.");
        }
    }

    @GetMapping("/activitats/listar")
    public ResponseEntity<?> ListarActividades() {
        try {
            List<Actividad> actividades = actividadService.listarActividades();
            if (actividades.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay actividades disponibles para exportar.");
            }
            return ResponseEntity.ok(actividades);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error inesperado.");
        }
    }
}
