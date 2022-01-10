package com.web.amrap.controladores;

import com.web.amrap.entidades.Ejercicio;
import com.web.amrap.entidades.Rutina;
import com.web.amrap.errores.ErrorServicio;
import com.web.amrap.implementacion.RutinaImplement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class RutinaController {

    @Autowired
    private RutinaImplement rutinaImplement;

    @Autowired
    private EjercicioController ejercicioController;

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/listar-rutinas")
    public String listarRutinas(ModelMap modelo) {

        try {

            List<Rutina> rutinas = rutinaImplement.listarRutinas();
            modelo.addAttribute("rutinas", rutinas);

        } catch (ErrorServicio ex) {

            modelo.put("error", ex.getMessage());
            return "/rutina/rutinas_busqueda.html";
        }

        return "/rutina/rutinas_busqueda.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/nueva-rutina")
    public String cargarRutina(ModelMap modelo) {

//        List<Rutina> rutinas = null;
//
//        try {
//
//            rutinas = rutinaImplement.listarRutinas();
//            modelo.put("rutinas", rutinas);
//
//        } catch (ErrorServicio ex) {
//
//            modelo.put("error", ex.getMessage());
//        }
        return "/rutina/rutinas_cargar.html";
    }

  
    @PostMapping("/cargar-rutina")
    public String rutinasCargar(ModelMap modelo, String objetivo) {

        Rutina rutina = new Rutina();
      

        rutina.setObjetivo(objetivo);
   
        try {

            rutinaImplement.crearRutina(objetivo, objetivo, objetivo);
            modelo.put("exito", "La rutina, se cargó correctamente");

        } catch (ErrorServicio ex) {

            modelo.put("error", ex.getMessage());

            modelo.put("objetivo", objetivo);

            return "/rutina/rutinas_cargar.html";
        }
        return "/rutina/rutinas_cargar.html";
    }
    
//    
//    @GetMapping("/buscar-rutina")
//    public String buscarEjercicio(ModelMap modelo,
//            @RequestParam(required = false) String idNombre,
//            @RequestParam(required = false) String idCategoria) {
//
//        List<EjercicioNombre> nombresEjercicios = null;
//        List<Categoria> categorias = null;
//
//        try {
//
//            nombresEjercicios = ejercicioNombreImplement.listarEjercicioNombre(); //manda la lista de nombre de ejercicios a la vista
//            modelo.put("nombresEjercicios", nombresEjercicios);
//
//            categorias = categoriaImplement.listarCategorias(); //manda la lista de nombre de categorias a la vista
//            modelo.put("categorias", categorias);
//
//            if (idNombre != null && !idNombre.isEmpty()) {
//
//                List<Ejercicio> listaEjercicios = ejercicioImplement.buscarPorNombre(idNombre);
//                modelo.addAttribute("listaEjercicios", listaEjercicios); // ver si asi se renderiza bien
//
//            } else if (idCategoria != null && !idCategoria.isEmpty()) {
//
//                List<Ejercicio> listaEjercicios = ejercicioImplement.buscarPorCategoria(idCategoria);
//                modelo.addAttribute("listaEjercicios", listaEjercicios);
//            }
//
//        } catch (ErrorServicio ex) {
//            modelo.put("error", ex.getMessage());
//            return "/rutina/rutinas_busqueda.html";
//        }
//
//        return "/rutina/rutinas_busqueda.html";
//    }

}
