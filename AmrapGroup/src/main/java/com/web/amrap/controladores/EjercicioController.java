package com.web.amrap.controladores;

import com.web.amrap.entidades.Ejercicio;
import com.web.amrap.errores.ErrorServicio;
import com.web.amrap.implementacion.EjercicioImplement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class EjercicioController {

//    @GetMapping("/")
//    public String index() {
//        return "inicio.html";
//    }
    @GetMapping("/inicio")
    public String inicio() {
        return "inicio.html";
    }

    @GetMapping("/ejercicios")
    public String ejercicios() {
        return "ejercicios.html";
    }

    @GetMapping("/ejercicios_cargar")
    public String ejerciciosCarga() {
        return "/ejercicios_cargar.html";
    }

    @GetMapping("ejercicios_busqueda")
    public String ejerciciosBusqueda() {
        return "/ejercicios_busqueda.html";
    }

    @Autowired
    private EjercicioImplement ejercicioImplement;

    @PostMapping("/cargar-ejercicio")
    public String cargarEjercicio(ModelMap modelo,
            @RequestParam String nombre,
            @RequestParam String categoria,
            @RequestParam(required = false) String video,
            @RequestParam(required = false) Integer series,
            @RequestParam(required = false) Integer repeticiones,
            @RequestParam(required = false) Integer pausa,
            @RequestParam(required = false) Integer dificultad,
            @RequestParam(required = false) String kilogramos,
            @RequestParam(required = false) String notas,
            @RequestParam(required = false) String atencion) {

        System.out.println("entra al metodo cargar-ejercicio. este es el nombre: " + nombre);

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

        try {        

            ejercicioImplement.crearEjercicio(nombre, categoria, video, series, repeticiones, pausa, dificultad, kilogramos, notas, atencion);

            modelo.put("exito", "El ejercicio, se cargó correctamente");

        } catch (ErrorServicio ex) {

            modelo.put("error", ex.getMessage());

            modelo.put("nombre", nombre);
            modelo.put("categoria", categoria);
            modelo.put("video", video);
            modelo.put("series", series);
            modelo.put("repeticiones", repeticiones);
            modelo.put("pausa", pausa);
            modelo.put("dificultad", dificultad);
            modelo.put("kilogramos", kilogramos);
            modelo.put("notas", notas);
            modelo.put("atencion", atencion);            

            return "/ejercicios_cargar.html";
        }
        return "/ejercicios_cargar.html";
    }
    
    

    @GetMapping("/buscar_ejercicio")
    public String buscarEjercicio(ModelMap modelo,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String categoria) {
        
        System.out.println("ver si llega el parametro a la busqueda. parametro: nombre" + nombre +"   caterogia: " + categoria);

        List<Ejercicio> listaEjercicios = new ArrayList<>();

        if (nombre != null && !nombre.isEmpty()) {

            listaEjercicios = ejercicioImplement.buscarPorNombre(nombre);

            for (Ejercicio lEjercicios : listaEjercicios) {

                System.out.println("\n" + "Ejercicio: " + lEjercicios);

            }

            modelo.addAttribute("listaEjercicios", listaEjercicios); // ver si asi se renderiza bien

            System.out.println("entro al metodo del nombre");

        } else if (categoria != null && !categoria.isEmpty()) {

            listaEjercicios = ejercicioImplement.buscarPorCategoria(categoria);

            for (Ejercicio lEjercicio : listaEjercicios) {

                System.out.println("\n" + "libro: " + lEjercicio);

            }

            modelo.put("listaEjercicios", listaEjercicios);

            System.out.println("entro al metodo de categoria");

        }

        return "/ejercicios_busqueda.html";
    }
}
