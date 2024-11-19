package com.provaTecnica.provaTecnica_activitats.repository;

import com.provaTecnica.provaTecnica_activitats.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
}
