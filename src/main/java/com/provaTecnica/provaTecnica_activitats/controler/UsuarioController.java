package com.provaTecnica.provaTecnica_activitats.controler;

import com.provaTecnica.provaTecnica_activitats.entities.Usuario;
import com.provaTecnica.provaTecnica_activitats.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appActivitats")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Crear un nuevo usuario
    @PostMapping("/user")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        try {
            String mensaje = usuarioService.crearUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error inesperado.");
        }
    }

    // Actualizar un usuario existente
    @PutMapping("/users/{id}")
    public ResponseEntity<?> actualizarUsuario(@RequestBody Usuario usuario, @PathVariable Long id) {
        try {
            String mensaje = usuarioService.actualizarUsuario(usuario, id);
            return ResponseEntity.ok(mensaje);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error inesperado.");
        }
    }

    // Consultar un usuario por ID
    @GetMapping("/users/{id}")
    public ResponseEntity<?> consultarUsuarioById(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.buscarUsuarioById(id);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error inesperado.");
        }
    }

    // Listar todos los usuarios
    @GetMapping("/users")
    public ResponseEntity<?> listarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.listarUsuarios();
            if (usuarios.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay usuarios registrados.");
            }
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error inesperado.");
        }
    }

    // Borrar un usuario por ID
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> borrarUsuarioById(@PathVariable Long id) {
        try {
            String mensaje = usuarioService.deleteUsuarioById(id);
            return ResponseEntity.ok(mensaje);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error inesperado.");
        }
    }
}
