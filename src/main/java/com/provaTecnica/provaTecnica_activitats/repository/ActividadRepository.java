package com.provaTecnica.provaTecnica_activitats.repository;

import com.provaTecnica.provaTecnica_activitats.entities.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad,Long> {
}
