package com.web.amrap.controladores;

import com.web.amrap.entidades.Ejercicio;
import com.web.amrap.entidades.Rutina;
import com.web.amrap.entidades.Usuario;
import com.web.amrap.errores.ErrorServicio;
import com.web.amrap.implementacion.RutinaImplement;
import com.web.amrap.implementacion.UsuarioImplement;
import java.util.List;
import javax.servlet.http.HttpSession;
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
    private UsuarioImplement usuarioImplement;
    
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/listar-rutinas")
    public String listarRutinasPorId(ModelMap modelo, HttpSession session) {

        try {

            Usuario login = (Usuario) session.getAttribute("usuariosession");

            List<Rutina> rutinas = rutinaImplement.buscarRutinaPorUsuario(login.getId());
            modelo.addAttribute("rutinas", rutinas);

        } catch (ErrorServicio ex) {

            modelo.put("error", ex.getMessage());
            return "/rutina/rutinas_busqueda.html";
        }

        return "/rutina/rutinas_busqueda.html";
    }
    
    

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/buscar-rutina")
    public String buscarRutina(ModelMap modelo, HttpSession session,
            @RequestParam (required = false) String idUsuario) {

        try {

            List<Rutina> rutinas = rutinaImplement.buscarRutinaPorUsuario(idUsuario);
            modelo.addAttribute("rutinas", rutinas);

        } catch (ErrorServicio ex) {

            modelo.put("error", ex.getMessage());
            return "/rutina/rutinas_busqueda.html";
        }

        return "/rutina/rutinas_buscar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/nueva-rutina")
    public String cargarRutina(ModelMap modelo, HttpSession session) {

        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuarioLogueado", usuarioLogueado);

        return "/rutina/rutinas_cargar.html";
    }
    
    

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/crear-rutina")
    public String cargarRutina(ModelMap modelo, HttpSession session,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String objetivo,
            @RequestParam String idUsuario) {

        System.out.println("**************** este es el id que entra como parametro: " + idUsuario);

        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(idUsuario)) {
            return "redirect:/inicio";
        }

        try {
            Usuario usuario = usuarioImplement.buscarUsuarioPorId(idUsuario);
            modelo.addAttribute("perfil", usuario);

            rutinaImplement.crearRutina(idUsuario, nombre, objetivo);

            modelo.put("exito", "La rutina, se cargó correctamente");

        } catch (ErrorServicio ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("objetivo", objetivo);

            return "redirect:/inicio";
        }
        return "redirect:/listar-rutinas";
    }
}