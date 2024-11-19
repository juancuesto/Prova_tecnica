package com.provaTecnica.provaTecnica_activitats.controler;

import com.provaTecnica.provaTecnica_activitats.entities.Usuario;
import com.provaTecnica.provaTecnica_activitats.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appActivitats")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/user")
    public ResponseEntity<?> crearUsuari(@RequestBody Usuario usuario){
        try {
            return usuarioService.crearUsuario(usuario);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return new ResponseEntity<>("HA OCURRIDO UN ERROR INESPERADO", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> actualizarUsuario(@RequestBody Usuario usuario, @PathVariable Long id){
        try {
            return usuarioService.actualizarUsuario(usuario,id);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return new ResponseEntity<>("HA OCURRIDO UN ERROR INESPERADO", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> consultarUsuarioById(@PathVariable Long id){
        try {
            return usuarioService.buscarUsuarioById(id);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return new ResponseEntity<>("HA OCURRIDO UN ERROR INESPERADO", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> borrarUsuarioById(@PathVariable Long id){
        try {
            return usuarioService.deleteUsuarioById(id);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return new ResponseEntity<>("HA OCURRIDO UN ERROR INESPERADO", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
