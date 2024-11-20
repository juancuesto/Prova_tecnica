package com.provaTecnica.provaTecnica_activitats.repository;

import com.provaTecnica.provaTecnica_activitats.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar usuario por email
    Optional<Usuario> findByEmail(String email);

    // Verificar si existe un usuario con un email específico
    boolean existsByEmail(String email);
}
