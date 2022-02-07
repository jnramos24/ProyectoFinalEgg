package com.web.amrap.controladores;

import Enumeraciones.Role;
import com.web.amrap.entidades.Usuario;
import com.web.amrap.errores.ErrorServicio;
import com.web.amrap.implementacion.UsuarioImplement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN') or hasRole('ROLE_USUARIO_REGISTRADO'))")
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
            @RequestParam String email,
            @RequestParam String clave1,
            @RequestParam String clave2) {

        try {

            usuarioImplement.registarUsuario(nombre, apellido, email, clave1, clave2, archivo);

            modelo.put("exito", "El usuario, se registró correctamente");

            return "redirect:/login";

        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("mail", email);
            modelo.put("clave1", clave1);
            modelo.put("clave2", clave2);

            return "registro.html";
        }
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

    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN') or hasRole('ROLE_USUARIO_REGISTRADO'))")
    @GetMapping("/mostrar-perfil")
    public String mostrarPerfil(ModelMap model, HttpSession session,
            @RequestParam(required = false) String id) {

        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/inicio";
        }

        model.put("login", login);

        return "mostrar_perfil.html";
    }

    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN') or hasRole('ROLE_USUARIO_REGISTRADO'))")
    @GetMapping("/editar-perfil")
    public String editarPerfil(ModelMap model, HttpSession session,
            @RequestParam(required = false) String id) {

        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/inicio";
        }

        try {

            Usuario usuario = usuario = usuarioImplement.buscarUsuarioPorId(id);

            model.put("roles", Role.values());

            model.addAttribute("perfil", usuario);

        } catch (ErrorServicio e) {
            model.addAttribute("error", e.getMessage());
        }
        return "perfil.html";
    }

    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN') or hasRole('ROLE_USUARIO_REGISTRADO'))")
    @PostMapping("/actualizar-perfil")
    public String actualizacion(ModelMap modelo, HttpSession session, MultipartFile archivo,
            @RequestParam String id,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String email,
            @RequestParam(required = false) Role rol) {

        if (rol == null) {
            rol = rol.USUARIO_REGISTRADO;
        }

        Usuario usuario = null;

        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null) {
            return "redirect:/inicio";
        }
        try {
            usuario = usuarioImplement.buscarUsuarioPorId(id);

            usuarioImplement.modificarUsuario(id, nombre, apellido, email, archivo, rol);

            modelo.put("exito", "El usuario, se modifico correctamente");

            session.setAttribute("usuariosession", usuario);

            return "redirect:/mostrar-perfil?id=" + id;

        } catch (ErrorServicio ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("perfil", usuario);

            return "perfil.html";
        }
    }

    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN') or hasRole('ROLE_USUARIO_REGISTRADO'))")
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

    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN') or hasRole('ROLE_USUARIO_REGISTRADO'))")
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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listar-usuarios")
    public String listarUsuarios(ModelMap modelo) {

        List<Usuario> listaUsuarios = null;

        try {

            listaUsuarios = usuarioImplement.listarUsuarios();
            modelo.addAttribute("listaUsuarios", listaUsuarios);

            return "/usuarios_busqueda.html";

        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "/usuarios_busqueda.html";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/buscar-usuario")
    public String buscarUsuario(ModelMap modelo,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String id) {

        List<Usuario> listaUsuarios = new ArrayList();

        try {

            if (nombre != null && !nombre.isEmpty()) {

                listaUsuarios = usuarioImplement.buscarUsuarioPorNombre(nombre);
                modelo.addAttribute("listaUsuarios", listaUsuarios);

            } else if (id != null && !id.isEmpty()) {

                Usuario usuario = usuarioImplement.buscarUsuarioPorId(id);

                listaUsuarios.add(usuario);
                modelo.addAttribute("listaUsuarios", listaUsuarios);
            }

            return "/usuarios_busqueda.html";

        } catch (ErrorServicio ex) {

            modelo.put("error", ex.getMessage());
            modelo.addAttribute("listaUsuarios", listaUsuarios);

            return "/usuarios_busqueda.html";
        }
    }

    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN'))")
    @GetMapping("/seleccionar-perfil")
    public String seleccionarUsuario(ModelMap modelo, HttpSession session,
            @RequestParam String id) {

        Usuario usuario = null;

        try {
            usuario = usuarioImplement.buscarUsuarioPorId(id);

            session.setAttribute("usuariosession", usuario);

            return "redirect:/mostrar-perfil?id=" + id;

        } catch (ErrorServicio ex) {

            modelo.put("error", ex.getMessage());

            return "perfil.html";
        }
    }

    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN') or hasRole('ROLE_USUARIO_REGISTRADO'))")
    @GetMapping("/deshabilitar-usuario")
    public String deshabilitarUsuario(ModelMap modelo, HttpSession session,
            @RequestParam String id) {

        Usuario usuario = null;

        try {
            Usuario login = (Usuario) session.getAttribute("usuariosession");
            if (login == null || !login.getId().equals(id)) {
                return "redirect:/inicio";
            }

            usuarioImplement.deshabilitarUsuario(id);

            modelo.put("exito", "El usuario se deshabilitó correctamente");

            usuario = usuarioImplement.buscarUsuarioPorId(id);
            modelo.put("usuario", usuario);

            return "redirect:/buscar-usuario?id=" + (usuario.getId());

        } catch (ErrorServicio ex) {

            modelo.put("error", ex.getMessage());

            return "/usuarios_busqueda.html";
        }
    }

    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN') or hasRole('ROLE_USUARIO_REGISTRADO'))")
    @GetMapping("/habilitar-usuario")
    public String habilitarUsuario(ModelMap modelo, HttpSession session,
            @RequestParam String id) {

        Usuario usuario = null;

        try {
            Usuario login = (Usuario) session.getAttribute("usuariosession");
            if (login == null || !login.getId().equals(id)) {
                return "redirect:/inicio";
            }

            usuarioImplement.habilitarUsuario(id);

            usuario = usuarioImplement.buscarUsuarioPorId(id);
            modelo.put("usuario", usuario);

            modelo.put("exito", "El usuario se habilitó correctamente");

            return "redirect:/buscar-usuario?id=" + (usuario.getId());

        } catch (ErrorServicio ex) {

            modelo.put("error", ex.getMessage());

            return "/usuarios_busqueda.html";
        }
    }

}
