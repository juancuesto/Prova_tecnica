package com.provaTecnica.provaTecnica_activitats.service;

import com.provaTecnica.provaTecnica_activitats.entities.Usuario;
import com.provaTecnica.provaTecnica_activitats.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Crear un nuevo usuario
    public String crearUsuario(Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().isEmpty()) {
            return "Debes introducir un nombre de usuario.";
        }
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            return "Debes introducir un email.";
        }
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            return "El email ya está registrado.";
        }
        usuarioRepository.save(usuario);
        return "Usuario creado exitosamente.";
    }

    // Listar todos los usuarios
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Buscar un usuario por su ID
    public Usuario buscarUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se ha encontrado el usuario con ID: " + id));
    }

    // Actualizar un usuario existente
    public String actualizarUsuario(Usuario usuario, Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()) {
            return "No se ha encontrado el usuario a actualizar.";
        }

        Usuario usuarioExistente = usuarioOptional.get();
        if (usuario.getNombre() != null) {
            usuarioExistente.setNombre(usuario.getNombre());
        }
        if (usuario.getEmail() != null) {
            usuarioExistente.setEmail(usuario.getEmail());
        }
        if (usuario.getApellidos() != null) {
            usuarioExistente.setApellidos(usuario.getApellidos());
        }
        if (usuario.getEdad() != null) {
            usuarioExistente.setEdad(usuario.getEdad());
        }
        if (usuario.getActividades() != null) {
            usuarioExistente.setActividades(usuario.getActividades());
        }

        usuarioRepository.save(usuarioExistente);
        return "Usuario actualizado correctamente.";
    }

    // Eliminar un usuario por su ID
    public String deleteUsuarioById(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()) {
            return "No se ha encontrado el usuario a borrar.";
        }
        usuarioRepository.deleteById(id);
        return "El usuario con ID: " + id + " ha sido borrado correctamente.";
    }
}
