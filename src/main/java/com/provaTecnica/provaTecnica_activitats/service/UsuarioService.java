package com.provaTecnica.provaTecnica_activitats.service;

import com.provaTecnica.provaTecnica_activitats.entities.Usuario;
import com.provaTecnica.provaTecnica_activitats.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ResponseEntity<?> crearUsuario(Usuario usuario){
        if (usuario.getNombre().isEmpty()){
            return new ResponseEntity<>("Debes introducir un nombre de usuario", HttpStatus.BAD_REQUEST);
        } else if (usuario.getEmail().isEmpty()) {
            return new ResponseEntity<>("Debes introducir un email",HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(usuarioRepository.save(usuario),HttpStatus.CREATED);
        }
    }

    public ResponseEntity<?> listarUsuarios(){
        List<Usuario> usuarios=usuarioRepository.findAll();
        if (usuarios.isEmpty()){
            return new ResponseEntity<>("No hay ningun registro a mostrar",HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(usuarios,HttpStatus.OK);
        }
    }

    public ResponseEntity<?> buscarUsuarioById(Long id){
        Optional<Usuario> usuarioOptional=usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()){
            return new ResponseEntity<>("No se ha encontrado el usuario con id: "+id,HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(usuarioOptional.get(),HttpStatus.OK);
        }
    }

    public ResponseEntity<?> actualizarUsuario(Usuario usuario,Long id){
        Optional<Usuario> usuarioOptional=usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()){
            return new ResponseEntity<>("No se ha encontrado el usuario a actualizar",HttpStatus.NOT_FOUND);
        }else {
            usuarioOptional.get().setNombre(usuario.getNombre());
            usuarioOptional.get().setEmail(usuario.getEmail());
            usuarioOptional.get().setApellidos(usuario.getApellidos());
            usuarioOptional.get().setEdad(usuario.getEdad());
            usuarioOptional.get().setActividades(usuario.getActividades());

            return new ResponseEntity<>(usuarioRepository.save(usuarioOptional.get()),HttpStatus.OK);
        }
    }
    public ResponseEntity<String> deleteUsuarioById(Long id){
        Optional<Usuario> usuarioOptional=usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()){
            return new ResponseEntity<>("No se ha encontrado el usuario a borrar",HttpStatus.NOT_FOUND);
        }else {
            usuarioRepository.deleteById(id);
            return new ResponseEntity<>("El registro con id: "+id+" se ha borrado correctamente",HttpStatus.OK);
        }
    }
}
