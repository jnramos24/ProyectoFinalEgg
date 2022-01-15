package com.web.amrap.controladores;

import com.web.amrap.entidades.Categoria;
import com.web.amrap.entidades.Ejercicio;
import com.web.amrap.entidades.EjercicioNombre;
import com.web.amrap.errores.ErrorServicio;
import com.web.amrap.implementacion.CategoriaImplement;
import com.web.amrap.implementacion.EjercicioImplement;
import com.web.amrap.implementacion.EjercicioNombreImplement;
import com.web.amrap.repositorios.CategoriaRepositorio;
import com.web.amrap.repositorios.EjercicioNombreRepositorio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class EjercicioController {

    @Autowired
    private EjercicioNombreRepositorio ejercicioNombreRepositorio;

    @Autowired
    private CategoriaImplement categoriaImplement;

    @Autowired
    private EjercicioImplement ejercicioImplement;

    @Autowired
    private EjercicioNombreImplement ejercicioNombreImplement;

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/listar-ejercicios")
    public String listarEjercicios(ModelMap modelo, @RequestParam String idRutina) { // este parametro se recibe por la url de otro metodo que llama a este

        modelo.put("idRutina", idRutina);   // se reenvia hacia la vista de crear ejercicio, el id, que se recibio por la url desde rutina 

        List<Ejercicio> listaEjercicios = null;
        List<Ejercicio> listaEjerc = null;
        List<Categoria> categorias = null;
        List<EjercicioNombre> nombresEjercicios = null;

        try {

            nombresEjercicios = ejercicioNombreImplement.listarEjercicioNombre(); //manda la lista de nombre de ejercicios a la vista
            modelo.put("nombresEjercicios", nombresEjercicios);

            categorias = categoriaImplement.listarCategorias(); //manda la lista de nombre de categorias a la vista
            modelo.put("categorias", categorias);

            listaEjercicios = ejercicioImplement.listarPorRutina(idRutina);//manda la lista de ejercicios a la vista
            modelo.addAttribute("listaEjercicios", listaEjercicios);

            listaEjerc = ejercicioImplement.listarEjercicios();//manda la lista de  ejercicios a la vista
            modelo.addAttribute("listaEjerc", listaEjerc);

            return "/ejercicio/ejercicios_busqueda.html";

        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "/ejercicio/ejercicios_busqueda.html";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/nuevo-ejercicio")
    public String cargarEjercicio(ModelMap modelo, @RequestParam String idRutina) { // este parametro se recibe por la url de otro metodo que llama a este

        modelo.put("idRutina", idRutina);   // se reenvia hacia la vista de crear ejercicio, el id, que se recibio por la url desde rutina     

        List<EjercicioNombre> nombres = null;

        try {

            nombres = ejercicioNombreImplement.listarEjercicioNombre();
            modelo.put("nombres", nombres);

        } catch (ErrorServicio ex) {

            modelo.put("error", ex.getMessage());
        }
        return "/ejercicio/ejercicios_cargar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/cargar-ejercicio")
    public String cargarEjercicio(ModelMap modelo,
            @RequestParam(required = false) Integer series,
            @RequestParam(required = false) Integer repeticiones,
            @RequestParam(required = false) Integer pausa,
            @RequestParam(required = false) Integer dificultad,
            @RequestParam(required = false) String kilogramos,
            @RequestParam(required = false) String notas,
            @RequestParam(required = false) String atencion,
            @RequestParam(required = false) String idEjercNom,
            @RequestParam(required = false) String idRutina) {

        try {

            List<EjercicioNombre> nombres = ejercicioNombreRepositorio.findAll();
            modelo.put("nombres", nombres);

            ejercicioImplement.crearEjercicio(series, repeticiones, pausa, dificultad, kilogramos, notas, atencion, idEjercNom, idRutina);

            modelo.put("exito", "El ejercicio, se cargó correctamente");

            return "redirect:/listar-ejercicios?idRutina=" + idRutina;

        } catch (ErrorServicio ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("series", series);
            modelo.put("repeticiones", repeticiones);
            modelo.put("pausa", pausa);
            modelo.put("dificultad", dificultad);
            modelo.put("kilogramos", kilogramos);
            modelo.put("notas", notas);
            modelo.put("atencion", atencion);

            return "/ejercicio/ejercicios_cargar.html";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/buscar-ejercicio")
    public String buscarEjercicio(ModelMap modelo,
            @RequestParam(required = false) String idNombre,
            @RequestParam(required = false) String idCategoria,
            @RequestParam String idRutina) { // este parametro se recibe por la url de otro metodo que llama a este

         modelo.put("idRutina", idRutina);   // se reenvia hacia la vista de crear ejercicio, el id, que se recibio por la url desde rutina  
        
        List<EjercicioNombre> nombresEjercicios = null;
        List<Categoria> categorias = null;

        try {

            nombresEjercicios = ejercicioNombreImplement.listarEjercicioNombre(); //manda la lista de nombre de ejercicios a la vista
            modelo.put("nombresEjercicios", nombresEjercicios);

            categorias = categoriaImplement.listarCategorias(); //manda la lista de nombre de categorias a la vista
            modelo.put("categorias", categorias);

            if (idNombre != null && !idNombre.isEmpty()) {

                List<Ejercicio> listaEjercicios = ejercicioImplement.buscarPorNombre(idNombre, idRutina);
                modelo.addAttribute("listaEjercicios", listaEjercicios); // ver si asi se renderiza bien

            } else if (idCategoria != null && !idCategoria.isEmpty()) {

                List<Ejercicio> listaEjercicios = ejercicioImplement.buscarPorCategoria(idCategoria, idRutina);
                modelo.addAttribute("listaEjercicios", listaEjercicios);
            }

        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "/ejercicio/ejercicios_busqueda.html";
        }

        return "/ejercicio/ejercicios_busqueda.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/modificar-ejercicio")
    public String modificarEjercicio(ModelMap modelo,
            @RequestParam String id) {

        try {

            List<EjercicioNombre> nombresEjercicios = ejercicioNombreImplement.listarEjercicioNombre(); //manda la lista de nombre de ejercicios a la vista
            modelo.put("nombresEjercicios", nombresEjercicios);

            Ejercicio ejercicio = ejercicioImplement.buscarEjercicioPorId(id);
            modelo.addAttribute("ejercicio", ejercicio);

        } catch (ErrorServicio ex) {

            modelo.addAttribute("error", ex.getMessage());

            return "/ejercicio/ejercicios_modificar.html";
        }
        return "/ejercicio/ejercicios_modificar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/actualizar-ejercicio")
    public String actualizarEjercicio(ModelMap modelo,
            @RequestParam String id,
            @RequestParam(required = false) Integer series,
            @RequestParam(required = false) Integer repeticiones,
            @RequestParam(required = false) Integer pausa,
            @RequestParam(required = false) Integer dificultad,
            @RequestParam(required = false) String kilogramos,
            @RequestParam(required = false) String notas,
            @RequestParam(required = false) String atencion,
            @RequestParam(required = false) String idEjercNom,
            @RequestParam(required = false) String idRutina) {

        Ejercicio ejercicio = null;

        try {

            List<EjercicioNombre> nombresEjercicios = ejercicioNombreImplement.listarEjercicioNombre(); //manda la lista de nombre de ejercicios a la vista
            modelo.put("nombresEjercicios", nombresEjercicios);

            ejercicio = ejercicioImplement.buscarEjercicioPorId(id);

            ejercicioImplement.modificarEjercicio(id, series, repeticiones,
                    pausa, dificultad, kilogramos, notas, atencion, idEjercNom, idRutina);

            ejercicio = ejercicioImplement.buscarEjercicioPorId(id); // devuelve el objeto modificado
            modelo.put("ejercicio", ejercicio);

            modelo.put("exito", "El ejercicio, se modificó correctamente");

        } catch (ErrorServicio ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("ejercicioNombre", ejercicio);

            return "/ejercicio/ejercicios_modificar.html";
        }
        return "/ejercicio/ejercicios_modificar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/eliminar-ejercicio")
    public String eliminarEjercicio(@RequestParam String id, ModelMap modelo) {

        try {
            ejercicioImplement.eliminarEjercicio(id);

            modelo.put("exito", "El ejercicio se eliminó correctamente");

        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());

            return "/ejercicio/ejercicios_busqueda.html";
        }
        return "/ejercicio/ejercicios_busqueda.html";
    }

}
