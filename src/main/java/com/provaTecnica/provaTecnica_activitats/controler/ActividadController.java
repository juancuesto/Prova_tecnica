package com.provaTecnica.provaTecnica_activitats.controler;

import com.provaTecnica.provaTecnica_activitats.entities.Actividad;
import com.provaTecnica.provaTecnica_activitats.service.ActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appActivitats")
public class ActividadController {

    @Autowired
    private ActividadService actividadService;

    @PostMapping("/activitat")
    public ResponseEntity<?> crearActivitat(@RequestBody Actividad actividad) {
        try {
            return actividadService.crearActividad(actividad);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>("HA OCURRIDO UN ERROR INESPERADO", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/activitats/{id}")
    public ResponseEntity<?> consultarActivitaById(@PathVariable Long id) {
        try {
            return actividadService.consultarActividadById(id);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>("HA OCURRIDO UN ERROR INESPERADO", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/apuntarseActividad/{usuario_id}/{actividad_id}")
    public ResponseEntity<?> apuntarseActividad(@PathVariable Long usuario_id, @PathVariable Long actividad_id) {
        try {
            return actividadService.apuntarseActividad(usuario_id, actividad_id);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>("HA OCURRIDO UN ERROR INESPERADO", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/importar")
    public ResponseEntity<?> importarActividades() {
        try {
            return actividadService.cargarActividades();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>("HA OCURRIDO UN ERROR INESPERADO", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/exportarActividades/{usuario_id}")
    public ResponseEntity<?> exportarActividades(@PathVariable Long usuario_id){
        try {
            return actividadService.exportarListadoActividades(usuario_id);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>("HA OCURRIDO UN ERROR INESPERADO", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
