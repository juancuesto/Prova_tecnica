package com.provaTecnica.provaTecnica_activitats.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "actividades")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;

    @Column(name = "num_usuarios", nullable = false)
    private Integer numUsuarios = 0; // Valor inicial para evitar nulos

    @Column(name = "capacidad_maxima", nullable = false)
    private Integer capacidadMaxima;

    @ManyToMany(mappedBy = "actividades", cascade = CascadeType.PERSIST)
    private List<Usuario> usuarios = new ArrayList<>();
}
