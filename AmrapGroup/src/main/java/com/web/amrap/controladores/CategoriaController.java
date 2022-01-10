package com.web.amrap.controladores;

import com.web.amrap.entidades.Categoria;
import com.web.amrap.errores.ErrorServicio;
import com.web.amrap.implementacion.CategoriaImplement;
import com.web.amrap.repositorios.CategoriaRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class CategoriaController {

    @Autowired
    private CategoriaImplement categoriaImplement;

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/listar-categorias")
    public String listar(ModelMap modelo) {

        List<Categoria> listaCategorias = null;
        List<Categoria> categorias = null;

        try {

            listaCategorias = categoriaImplement.listarCategorias();
            modelo.addAttribute("listaCategorias", listaCategorias);

            categorias = categoriaImplement.listarCategorias();
            modelo.addAttribute("categorias", categorias);

        } catch (ErrorServicio ex) {

            modelo.put("error", ex.getMessage());
            return "/categoria/categorias_busqueda.html";
        }
        return "/categoria/categorias_busqueda.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/nueva-categoria")
    public String nuevaCategoria() {
        return "/categoria/categorias_cargar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/cargar-categoria")
    public String guardarCategoria(ModelMap modelo, @RequestParam String nombre) {

        try {
            categoriaImplement.crearCategoria(nombre);
            modelo.put("exito", "La categoría, se cargó correctamente");

        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);

            return "/categoria/categorias_cargar.html";
        }
        return "redirect:/listar-categorias";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/buscar-categoria")
    public String buscarCategoria(ModelMap modelo, @RequestParam(required = false) String nombre) {

        List<Categoria> categorias = null;
        List<Categoria> listaCategorias = null;

        try {

            categorias = categoriaImplement.listarCategorias();
            modelo.addAttribute("categorias", categorias);

            listaCategorias = categoriaImplement.buscarPorNombre(nombre);
            modelo.addAttribute("listaCategorias", listaCategorias);

        } catch (ErrorServicio ex) {

            modelo.put("error", ex.getMessage());
            modelo.addAttribute("listaCategorias", listaCategorias);
            return "/categoria/categorias_busqueda.html";
        }
        return "/categoria/categorias_busqueda.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/modificar-categoria")
    public String editarCategoria(@RequestParam String id, ModelMap modelo) {

        Categoria categoria = null;

        try {

            categoria = categoriaImplement.buscarCategoriaPorId(id);
            modelo.addAttribute("categoria", categoria);

        } catch (ErrorServicio ex) {

            modelo.addAttribute("error", ex.getMessage());

            return "/categoria/categorias_modificar.html";
        }
        return "/categoria/categorias_modificar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/actualizar-categoria")
    public String actualizarCategoria(ModelMap modelo, @RequestParam String id, @RequestParam String nombre) {

        Categoria categoria = null;

        try {

            categoria = categoriaImplement.buscarCategoriaPorId(id);

            categoriaImplement.modificarCategoria(id, nombre);

            categoria = categoriaImplement.buscarCategoriaPorId(id); // devuelve el objeto modificado
            modelo.put("categoria", categoria);

            modelo.put("exito", "La categoría se modificó correctamente");

        } catch (ErrorServicio ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("categoria", categoria);

            return "/categoria/categorias_modificar.html";
        }
        return "/categoria/categorias_modificar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/eliminar-categoria")
    public String eliminarCategoria(ModelMap modelo, @RequestParam String id) {

        List<Categoria> categorias = null;

        try {
            categorias = categoriaImplement.listarCategorias();
            modelo.addAttribute("categorias", categorias);

            categoriaImplement.eliminarCategoria(id);
            modelo.put("exito", "La categoría se eliminó correctamente");

        } catch (ErrorServicio ex) {

            modelo.put("error", ex.getMessage());
            modelo.addAttribute("categorias", categorias);

            return "/categoria/categorias_busqueda.html";
        }
        return "redirect:/listar-categorias";
    }

}
