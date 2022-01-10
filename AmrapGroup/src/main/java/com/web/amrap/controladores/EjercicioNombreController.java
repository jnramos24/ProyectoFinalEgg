package com.web.amrap.controladores;

import com.web.amrap.entidades.Categoria;
import com.web.amrap.entidades.EjercicioNombre;
import com.web.amrap.errores.ErrorServicio;
import com.web.amrap.implementacion.CategoriaImplement;
import com.web.amrap.implementacion.EjercicioNombreImplement;
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
public class EjercicioNombreController {

    @Autowired
    private EjercicioNombreImplement ejercicioNombreImplement;

    @Autowired
    private CategoriaImplement categoriaImplement;

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/listar-ejercicioNombres")
    public String listarEjercicioNombres(ModelMap modelo) {

        List<EjercicioNombre> listaEjercNomb = null;
        List<EjercicioNombre> listaEjerciciosNombres = null;
        List<Categoria> categorias = null;

        try {

            listaEjercNomb = ejercicioNombreImplement.listarEjercicioNombre();//manda la lista de nombres de ejercicios a la vista
            modelo.addAttribute("listaEjercNomb", listaEjercNomb);

            listaEjerciciosNombres = ejercicioNombreImplement.listarEjercicioNombre();//manda la lista de nombres de ejercicios a la vista
            modelo.addAttribute("listaEjerciciosNombres", listaEjerciciosNombres);

            categorias = categoriaImplement.listarCategorias();// manda la lista de categorias a la vista
            modelo.addAttribute("categorias", categorias);

        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "/ejercicio_nombre/ejercicioNombre_busqueda.html";
        }
        return "/ejercicio_nombre/ejercicioNombre_busqueda.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/nuevo-ejercicioNombre")
    public String cargarEjercicioNombre(ModelMap modelo) {

        List<Categoria> categorias = null;

        try {
            categorias = categoriaImplement.listarCategorias();// manda la lista de categorias a la vista
            modelo.put("categorias", categorias);

        } catch (ErrorServicio ex) {

            modelo.put("error", ex.getMessage());
        }
        return "/ejercicio_nombre/ejercicioNombre_cargar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/cargar-ejercicioNombre")
    public String cargarEjercicioNombre(ModelMap modelo,
            @RequestParam String nombre,
            @RequestParam String video,
            @RequestParam String idCategoria) {

        List<Categoria> categorias = null;

        try {
            categorias = categoriaImplement.listarCategorias();// manda la lista de categorias a la vista
            modelo.put("categorias", categorias);

            ejercicioNombreImplement.crearEjercicioNombre(nombre, video, idCategoria);
            modelo.put("exito", "El nombre del ejercicio, se cargó correctamente");

        } catch (ErrorServicio ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);

            return "/ejercicio_nombre/ejercicioNombre_cargar.html";
        }
        return "redirect:/listar-ejercicioNombres";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/buscar-ejercicioNombre")
    public String buscarEjercicioNombre(ModelMap modelo,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String idCategoria) {

        List<EjercicioNombre> listaEjercNomb = null;
        List<Categoria> categorias = null;

        try {
            listaEjercNomb = ejercicioNombreImplement.listarEjercicioNombre();//manda la lista de nombre de ejercicios a la vista
            modelo.addAttribute("listaEjercNomb", listaEjercNomb);

            categorias = categoriaImplement.listarCategorias();// manda la lista de categorias a la vista
            modelo.addAttribute("categorias", categorias);

            if (nombre != null && !nombre.isEmpty()) {

                List<EjercicioNombre> listaEjerciciosNombres = ejercicioNombreImplement.buscarPorNombre(nombre);
                modelo.addAttribute("listaEjerciciosNombres", listaEjerciciosNombres);  // manda la lista de nombres segun la busqueda

            } else if (idCategoria != null && !idCategoria.isEmpty()) {

                List<EjercicioNombre> listaEjerciciosNombres = ejercicioNombreImplement.buscarPorCategoria(idCategoria);
                modelo.addAttribute("listaEjerciciosNombres", listaEjerciciosNombres);  // manda la lista de nombres segun la busqueda
            }

        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "/ejercicio_nombre/ejercicioNombre_busqueda.html";
        }
        return "/ejercicio_nombre/ejercicioNombre_busqueda.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/modificar-ejercicioNombre")
    public String modificarEjercicioNombre(@RequestParam String id, ModelMap modelo) {

        try {

            List<Categoria> categorias = categoriaImplement.listarCategorias();// manda la lista de categorias a la vista
            modelo.addAttribute("categorias", categorias);

            EjercicioNombre ejercicioNombre = ejercicioNombreImplement.buscarEjercicioNombrePorId(id);
            modelo.addAttribute("ejercicioNombre", ejercicioNombre);

        } catch (ErrorServicio ex) {

            modelo.addAttribute("error", ex.getMessage());

            return "/ejercicio_nombre/ejercicioNombre_modificar.html";
        }
        return "/ejercicio_nombre/ejercicioNombre_modificar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/actualizar-ejercicioNombre")
    public String actualizarEjercicioNombre(ModelMap modelo,
            @RequestParam String id,
            @RequestParam String nombre,
            @RequestParam String video,
            @RequestParam String idCategoria) {

        EjercicioNombre ejercicioNombre = null;

        try {
            List<Categoria> categorias = categoriaImplement.listarCategorias();// manda la lista de categorias a la vista
            modelo.addAttribute("categorias", categorias);// revisar

            ejercicioNombre = ejercicioNombreImplement.buscarEjercicioNombrePorId(id);

            ejercicioNombreImplement.modificarEjercicioNombre(id, nombre, video, idCategoria);

            ejercicioNombre = ejercicioNombreImplement.buscarEjercicioNombrePorId(id); // devuelve el objeto modificado
            modelo.put("ejercicioNombre", ejercicioNombre);

            modelo.put("exito", "El ejercicioNombre se modificó correctamente");

        } catch (ErrorServicio ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("ejercicioNombre", ejercicioNombre);

            return "/ejercicio_nombre/ejercicioNombre_modificar.html";
        }
        return "/ejercicio_nombre/ejercicioNombre_modificar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/eliminar-ejercicioNombre")
    public String eliminarEjercicioNombre(@RequestParam String id, ModelMap modelo) {

        try {
            ejercicioNombreImplement.eliminarEjercicioNombre(id);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "/ejercicio_nombre/ejercicioNombre_busqueda.html";
        }
        return "redirect:/listar-ejercicioNombres";
    }
}
