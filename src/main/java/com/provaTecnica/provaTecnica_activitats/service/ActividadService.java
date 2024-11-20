package com.provaTecnica.provaTecnica_activitats.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.provaTecnica.provaTecnica_activitats.entities.Actividad;
import com.provaTecnica.provaTecnica_activitats.entities.Usuario;
import com.provaTecnica.provaTecnica_activitats.repository.ActividadRepository;
import com.provaTecnica.provaTecnica_activitats.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ActividadService {
    @Autowired
    private ActividadRepository actividadRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Actividad> listadoActividadesDisponibles(){
                log.info("estamos dentro de listado de skins disponibles");
        String ruta=".\\src\\main\\resources\\data\\activitats.json";
        List<Actividad> listadoActividades=new ArrayList<>();
        try{
            String contenidoJson= new String(Files.readAllBytes(Paths.get(ruta)));

            ObjectMapper objectMapper=new ObjectMapper();

            TypeReference<List<Actividad>> jacksonTypeReference=new TypeReference<List<Actividad>>() {};
            listadoActividades=objectMapper.readValue(contenidoJson,jacksonTypeReference);
        }catch (IOException exception){
            System.out.println("error: "+exception.getMessage());
        }
        return listadoActividades;

    }
    public ResponseEntity<?> crearActividad(Actividad actividad){
        if (actividad.getNombre().isEmpty()){
            return new ResponseEntity<>("Falta introducir el nombre de la actividad",HttpStatus.BAD_REQUEST);
        } else if (actividad.getDescripcion().isEmpty()) {
            return new ResponseEntity<>("Falta introducir la dsecripcion de la actividad",HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(actividadRepository.save(actividad),HttpStatus.CREATED);
        }
    }
    public  ResponseEntity<?> cargarActividadesEnBaseDatos(){
        try {
            List<Actividad> listadoActividades=this.listadoActividadesDisponibles();

            for (Actividad ele:listadoActividades) {
                this.crearActividad(ele);
            }
            return new ResponseEntity<>("el listado de actividades se ha cargado correctamente",HttpStatus.OK);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return new ResponseEntity<>("No se ha podido cargar el listado de Actividades en base de datos",HttpStatus.NOT_ACCEPTABLE);
    }
    public ResponseEntity<?> consultarActividadById(Long actividad_id){
        Optional<Actividad> actividadOptional=actividadRepository.findById(actividad_id);
        if (actividadOptional.isEmpty()) {
            return new ResponseEntity<>("No se ha encontrado la actividad con id: "+actividad_id,HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(actividadOptional.get(),HttpStatus.FOUND);
        }
    }

    public ResponseEntity<?> consultaActividadesUsuario(Long usuario_id){
        Optional<Usuario> usuarioOptional=usuarioRepository.findById(usuario_id);
        if (usuarioOptional.isEmpty()){
            return new ResponseEntity<>("No se ha encontrado el usuario con id: "+usuario_id,HttpStatus.NOT_FOUND);
        }else {
            List<Actividad> listadoActividades=usuarioOptional.get().getActividades();
            return new ResponseEntity<>(listadoActividades,HttpStatus.OK);
        }
    }
    public ResponseEntity<?> apuntarseActividad(Long usuario_id,Long actividad_id){
        Optional<Usuario> usuarioOptional=usuarioRepository.findById(usuario_id);
        if (usuarioOptional.isEmpty()){
            return new ResponseEntity<>("No se ha encontrado el usuario con id: "+usuario_id,HttpStatus.NOT_FOUND);
        }else {
            Optional<Actividad> actividadOptional=actividadRepository.findById(actividad_id);
            if (actividadOptional.isEmpty()){
                return new ResponseEntity<>("No se ha encontrado la actividad",HttpStatus.NOT_FOUND);
            } else if (actividadOptional.get().apuntarseActividad()) {
                usuarioOptional.get().afegirActividad(actividadOptional.get());
                Usuario usuario=usuarioRepository.save(usuarioOptional.get());
                return new ResponseEntity<>("El usuario "+usuario.getNombre()+" se ha apuntado a la actividad "+actividadOptional.get().getNombre()+ " correctamente",HttpStatus.OK);
            }else {
                return new ResponseEntity<>("La actividad no tiene plazas disponibles",HttpStatus.NOT_ACCEPTABLE);
            }
        }
    }

    public List<Actividad> actividadesDisponibles(){
        String ruta=".\\src\\main\\resources\\data\\activitats.json";
        List<Actividad> listadoActividades=new ArrayList<>();

        try{
            String contenidoJson= new String(Files.readAllBytes(Paths.get(ruta)));

            ObjectMapper objectMapper=new ObjectMapper();

            TypeReference<List<Actividad>> jacksonTypeReference=new TypeReference<List<Actividad>>() {};
            listadoActividades=objectMapper.readValue(contenidoJson,jacksonTypeReference);

        } catch (IOException exception) {
            System.out.println("error: "+exception.getMessage());
        }
        return listadoActividades;
    }

    public ResponseEntity<?> cargarActividades(){
        try{
            log.info("estamos cargando actividades");
            List<Actividad> listadoActividades=this.listadoActividadesDisponibles();
            for (Actividad ele:listadoActividades) {
                System.out.println("la actividad cargada es: "+ele);
                this.crearActividad(ele);
            }
            return new ResponseEntity<>("El listado de actividades se ha cargado correctamente",HttpStatus.OK);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return new ResponseEntity<>("No se ha podido cargar el listado de actividades en base de datos",HttpStatus.NOT_ACCEPTABLE);
    }

    public ResponseEntity<?> exportarListadoActividades(Long usuario_id){
        try {
            log.info("estamos exportando actividades");
            Optional<Usuario> usuarioOptional=usuarioRepository.findById(usuario_id);
            if (usuarioOptional.isEmpty()){
                log.info("No se encontro el uuuusususususususurioooooooo");
                return new ResponseEntity<>("No se ha encontrado el usuario buscado",HttpStatus.NOT_FOUND);
            }else {
                try{
                    List<Actividad> listado=usuarioOptional.get().getActividades();
                    ObjectMapper objectMapper=new ObjectMapper();
                    List<String> listadoJson=new ArrayList<>();


                    FileOutputStream fichero=new FileOutputStream("C:\\Users\\juanc\\Desktop\\java_ejemplo\\actividad.json");
                    StringWriter writer=new StringWriter();

                    for (Actividad ele:listado) {
                        objectMapper.writeValue(writer,ele);
                        listadoJson.add(writer.toString());
                    }

                    for (String ele:listadoJson) {
                        objectMapper.writeValue(fichero,ele);

                    }
                    fichero.close();
                    return new ResponseEntity<>("El fichero se ha exportado correctamente",HttpStatus.OK);

                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }
}
