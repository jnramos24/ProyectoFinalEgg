package com.web.amrap.controladores;

import com.web.amrap.entidades.Usuario;
import com.web.amrap.errores.ErrorServicio;
import com.web.amrap.implementacion.UsuarioImplement;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class UsuarioController {

    @Autowired
    private UsuarioImplement usuarioImplement;

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/inicio")
    public String inicio() {
        return "inicio.html";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro.html";
    }

    @PostMapping("/registrar")
    public String registrar(ModelMap modelo, MultipartFile archivo,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String dni,
            @RequestParam String email,
            @RequestParam String clave1,
            @RequestParam String clave2) {

        try {
            usuarioImplement.registarUsuario(nombre, apellido, dni, email, clave1, clave2, null);

            modelo.put("exito", "El usuario, se registró correctamente");

        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("dni", dni);
            modelo.put("mail", email);
            modelo.put("clave1", clave1);
            modelo.put("clave2", clave2);

            return "registro.html";
        }
        return "redirect:/login";
//        return "registrado.html";// lu

    }

    @GetMapping("/login")
    public String login(ModelMap model,
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String logout) {

        if (error != null) {
            model.put("error", "El usuario o clave ingresados no son correctos");
        }
        if (logout != null) {
            model.put("logout", "Ha salido correctamente de la plataforma");

            return "login.html";
        }

        return "login.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/mostrar-perfil")
    public String mostrarPerfil(ModelMap model, HttpSession session,
            @RequestParam(required = false) String id) {

        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {

            System.out.println("Este es el login: " + login + "   esta es el idlogin: " + login.getId() + "  y este es e l id que entra; " + id);
            return "redirect:/inicio";

        }

        return "mostrar_perfil.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/editar-perfil")
    public String editarPerfil(ModelMap model, HttpSession session,
            @RequestParam(required = false) String id) {

        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {

            System.out.println("Este es el login: " + login + "   esta es el idlogin: " + login.getId() + "  y este es e l id que entra; " + id);
            return "redirect:/inicio";

        }

        try {
            Usuario usuario = usuarioImplement.buscarUsuarioPorId(login.getId());
            model.addAttribute("perfil", usuario);
        } catch (ErrorServicio e) {
            model.addAttribute("error", e.getMessage());
        }
        return "perfil.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/actualizar-perfil")
    public String actualizacion(ModelMap modelo, HttpSession session, MultipartFile archivo,
            @RequestParam String id,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String dni,
            @RequestParam String email) {

        Usuario usuario = null;

        try {
            Usuario login = (Usuario) session.getAttribute("usuariosession");
            if (login == null || !login.getId().equals(id)) {
                return "redirect:/inicio";
            }
            usuario = usuarioImplement.buscarUsuarioPorId(id);

            usuarioImplement.modificarUsuario(id, nombre, apellido, dni, email, archivo);

            modelo.put("exito", "El usuario, se modifico correctamente");

            session.setAttribute("usuariosession", usuario);
            
            return "redirect:/mostrar-perfil?id="+login.getId();

        } catch (ErrorServicio ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("perfil", usuario);

            return "perfil.html";
        }

        
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/modificar-clave")
    public String modificarClave(ModelMap model, HttpSession session,
            @RequestParam String id) {

        model.addAttribute("cambiarClave", "cambiarClave");

        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/inicio";
        }

        try {
            Usuario usuario = usuarioImplement.buscarUsuarioPorId(id);
            model.addAttribute("perfil", usuario);

        } catch (ErrorServicio e) {

            model.addAttribute("error", e.getMessage());
        }
        return "clave.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/actualizar-clave")
    public String actualizacionClave(ModelMap modelo, HttpSession session,
            @RequestParam String id,
            @RequestParam String clave1,
            @RequestParam String clave2) {

        Usuario usuario = null;

        try {
            Usuario login = (Usuario) session.getAttribute("usuariosession");
            if (login == null || !login.getId().equals(id)) {
                return "redirect:/inicio";
            }
            usuario = usuarioImplement.buscarUsuarioPorId(id);

            usuarioImplement.modificarClaveUsuario(id, clave1, clave2);
            session.setAttribute("usuariosession", usuario);

            modelo.put("exito", "La clave se modificó correctamente");

            return "inicio.html";

        } catch (ErrorServicio ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("perfil", usuario);

            return "clave.html";
        }
    }

}
