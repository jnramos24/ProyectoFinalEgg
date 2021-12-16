/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.amrap.implementacion;

import com.web.amrap.entidades.Ejercicio;
import com.web.amrap.errores.ErrorServicio;
import com.web.amrap.repositorios.EjercicioRepositorio;
import com.web.amrap.servicios.EjercicioService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EjercicioImplement implements EjercicioService {

    @Autowired
    EjercicioRepositorio ejercicioRepositorio;

    @Transactional
    @Override
    public void crearEjercicio(String nombre, String categoria,String video, Integer series, Integer repeticiones, 
            Integer pausa, Integer dificultad,String kilogramos, String notas, String atencion) throws ErrorServicio {

        validarDatos(nombre, categoria, video, series, repeticiones, pausa, dificultad, kilogramos);

        Ejercicio ejercicio = new Ejercicio();

        ejercicio.setNombre(nombre);
        ejercicio.setCategoria(categoria);
        ejercicio.setVideo(video);
        ejercicio.setSeries(series);
        ejercicio.setRepeticiones(repeticiones);
        ejercicio.setPausa(pausa);
        ejercicio.setDificultad(dificultad);
        ejercicio.setKilogramos(kilogramos);
        ejercicio.setNotas(notas);
        ejercicio.setAtencion(atencion);

        ejercicioRepositorio.save(ejercicio);
    }

    @Transactional
    @Override
    public void modificarEjercicio(String id, String nombre, String categoria, String video, Integer series, Integer repeticiones, 
            Integer pausa, Integer dificultad, String kilogramos, String notas, String atencion) throws ErrorServicio {

        validarDatos(nombre, categoria, video, series, repeticiones, pausa, dificultad, kilogramos);

        Optional<Ejercicio> respuestaEjercicio = ejercicioRepositorio.findById(id);

        if (respuestaEjercicio.isPresent()) {

            Ejercicio ejercicio = respuestaEjercicio.get();

            ejercicio.setNombre(nombre);
            ejercicio.setCategoria(categoria);
            ejercicio.setVideo(video);
            ejercicio.setSeries(series);
            ejercicio.setRepeticiones(repeticiones);
            ejercicio.setPausa(pausa);
            ejercicio.setDificultad(dificultad);
            ejercicio.setKilogramos(kilogramos);
            ejercicio.setNotas(notas);
            ejercicio.setAtencion(atencion);

            ejercicioRepositorio.save(ejercicio);
        } else {
            throw new ErrorServicio("No se encontró ningun ejercicio con este id.");
        }

    }

    @Transactional
    @Override
    public void eliminarEjercicio(String id) throws ErrorServicio {

        Optional<Ejercicio> respuestaEjercicio = ejercicioRepositorio.findById(id);

        if (respuestaEjercicio.isPresent()) {

            ejercicioRepositorio.deleteById(id);

        } else {
            throw new ErrorServicio("No se encontró ningun ejercicio con este id.");
        }

    }

    @Transactional
    @Override
    public Ejercicio buscarEjercicioPorId(String id) throws ErrorServicio {

        Optional<Ejercicio> respuesta = ejercicioRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Ejercicio ejercicio = respuesta.get();

            return ejercicio;

        } else {
            throw new ErrorServicio("No se encontro ningun ejercicio con este id.");
        }

    }

    @Transactional
    @Override
    public List<Ejercicio> buscarPorNombre(String nombre) {

        List<Ejercicio> ejercicios = new ArrayList<>();

        return ejercicios = ejercicioRepositorio.buscarPorNombre(nombre);
    }

    @Transactional
    @Override
    public List<Ejercicio> buscarPorCategoria(String cateogoria) {

        List<Ejercicio> ejercicios = new ArrayList<>();

        return ejercicios = ejercicioRepositorio.buscarPorCategoria(cateogoria);
    }

    @Override
    public void validarDatos(String nombre, String categoria, String video, Integer series, Integer repeticiones, Integer pausa, 
            Integer dificultad, String kilogramos) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del ejercicio, no puede ser nulo.");
        }

        if (categoria == null || categoria.isEmpty()) {
            throw new ErrorServicio("La categoría del ejercicio, no puede ser nula.");
        }

        if (series == null) {
            throw new ErrorServicio("Las series, no pueden ser nulas.");
        }

        if (repeticiones == null) {
            throw new ErrorServicio("Las repeticiones, no pueden ser nulas.");
        }

        if (pausa == null) {
            throw new ErrorServicio("La pausa, no pueden ser nula.");
        }

        if (dificultad == null) {
            throw new ErrorServicio("La dificultad, no pueden ser nula.");
        }

        if (kilogramos == null || kilogramos.isEmpty()) {
            throw new ErrorServicio("Los kilogramos, no pueden ser nulos.");
        }

    }

}
