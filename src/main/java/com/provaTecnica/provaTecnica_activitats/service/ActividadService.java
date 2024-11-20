package com.provaTecnica.provaTecnica_activitats.service;

import com.provaTecnica.provaTecnica_activitats.entities.Actividad;
import com.provaTecnica.provaTecnica_activitats.entities.Usuario;
import com.provaTecnica.provaTecnica_activitats.repository.ActividadRepository;
import com.provaTecnica.provaTecnica_activitats.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Crear una nueva actividad
    public String crearActividad(Actividad actividad) {
        if (actividad.getNombre() == null || actividad.getNombre().isEmpty()) {
            return "El nombre de la actividad es obligatorio";
        }
        if (actividad.getDescripcion() == null || actividad.getDescripcion().isEmpty()) {
            return "La descripción de la actividad es obligatoria";
        }

        if (actividadRepository.existsByNombre(actividad.getNombre())) {
            return "Ya existe una actividad con ese nombre";
        }

        actividadRepository.save(actividad);
        return "Actividad creada exitosamente";
    }

    // Consultar todas las actividades disponibles
    public List<Actividad> listarActividades() {
        return actividadRepository.findAll();
    }

    // Consultar una actividad por su ID
    public Actividad consultarActividadPorId(Long actividadId) {
        return actividadRepository.findById(actividadId).orElse(null);
    }

    // Apuntarse a una actividad
    public String apuntarseActividad(Long usuarioId, Long actividadId) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        if (usuarioOptional.isEmpty()) {
            return "No se ha encontrado el usuario con ID: " + usuarioId;
        }

        Optional<Actividad> actividadOptional = actividadRepository.findById(actividadId);
        if (actividadOptional.isEmpty()) {
            return "No se ha encontrado la actividad con ID: " + actividadId;
        }

        Actividad actividad = actividadOptional.get();
        if (actividad.getNumUsuarios() >= actividad.getCapacidadMaxima()) {
            return "La actividad no tiene plazas disponibles";
        }

        // Incrementar el número de usuarios en la actividad
        actividad.setNumUsuarios(actividad.getNumUsuarios() + 1);

        // Asociar el usuario a la actividad
        Usuario usuario = usuarioOptional.get();
        usuario.getActividades().add(actividad);

        // Guardar los cambios
        usuarioRepository.save(usuario);
        actividadRepository.save(actividad);

        return "El usuario " + usuario.getNombre() + " se ha apuntado a la actividad " + actividad.getNombre() + " correctamente";
    }

    // Importar actividades desde una lista
    public String importarActividades(List<Actividad> actividades) {
        if (actividades == null || actividades.isEmpty()) {
            return "La lista de actividades está vacía o es nula";
        }

        List<String> errores = new ArrayList<>();
        for (Actividad actividad : actividades) {
            String resultado = crearActividad(actividad);
            if (!"Actividad creada exitosamente".equals(resultado)) {
                errores.add("Error al importar actividad: " + actividad.getNombre() + " - " + resultado);
            }
        }

        if (!errores.isEmpty()) {
            return "Errores encontrados durante la importación: " + String.join("; ", errores);
        }

        return "Todas las actividades se han importado correctamente";
    }

    // Exportar todas las actividades como una lista
    public List<Actividad> exportarActividades() {
        return listarActividades();
    }
}
