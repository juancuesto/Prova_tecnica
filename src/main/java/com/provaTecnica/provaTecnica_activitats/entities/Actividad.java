package com.provaTecnica.provaTecnica_activitats.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "actividades")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private Integer numUsuarios;

    @Column(name = "capacidad_maxima")
    private Integer capacidadMaxima;

    @ManyToMany(mappedBy = "actividades")
    private List<Usuario> usuarios=new ArrayList<>();

        public Boolean apuntarseActividad(){
        if (numUsuarios<capacidadMaxima){
            this.numUsuarios=numUsuarios+1;
            return true;
        }else {
            return false;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getNumUsuarios() {
        return numUsuarios;
    }

    public void setNumUsuarios(Integer numUsuarios) {
        this.numUsuarios = numUsuarios;
    }

    public Integer getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(Integer capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
