package com.provaTecnica.provaTecnica_activitats.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String nombre;
    private String apellidos;
    private Integer edad;
    private String email;

    @ManyToMany
    @JoinTable(name = "usuario_actividad",
    joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "actividad_id"))
    @JsonIgnore
    private List<Actividad> actividades;

    public void afegirActividad(Actividad actividad){
        this.actividades.add(actividad);
    }
}
