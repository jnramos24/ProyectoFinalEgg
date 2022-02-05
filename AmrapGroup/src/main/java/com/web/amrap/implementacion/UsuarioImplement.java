package com.web.amrap.implementacion;

import Enumeraciones.Role;
import com.web.amrap.entidades.Foto;
import com.web.amrap.entidades.Usuario;
import com.web.amrap.errores.ErrorServicio;
import com.web.amrap.repositorios.UsuarioRepositorio;
import com.web.amrap.servicios.MailService;
import com.web.amrap.servicios.UsuarioService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioImplement implements UsuarioService, UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private FotoImplement fotoImplement;
    
    @Autowired
    private MailService mailService; 

    @Transactional
    @Override
    public void registarUsuario(String nombre, String apellido, String email, String clave1, String clave2, MultipartFile archivo) throws ErrorServicio {

        validarDatos(nombre, apellido, email);
        validarClaves(clave1, clave2);

        if (usuarioRepositorio.buscarUsuarioPorEmail(email) == null) {

            Usuario usuario = new Usuario();

            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setEmail(email);
            usuario.setAlta(new Date());
            usuario.setBaja(null);
            usuario.setRol(Role.USUARIO_REGISTRADO);

            String claveEncriptada = new BCryptPasswordEncoder().encode(clave1);
            usuario.setClave(claveEncriptada);

            if (archivo != null && !archivo.isEmpty()) {

                Foto foto = fotoImplement.guardarFoto(archivo);
                usuario.setFoto(foto);
                usuarioRepositorio.save(usuario);
            } else {
                Foto foto = fotoImplement.buscarFoto("70c6ae74-6370-4262-9360-790001147617");
                usuario.setFoto(foto);
                usuarioRepositorio.save(usuario);
                
                try {      
            mailService.enviaMail("Estoy enviando una imagen","Bienvenidos a AMRAP" , usuario.getEmail(), "C:\\ProyectoFinalEgg\\AmrapGroup\\src\\main\\resources\\static\\img\\Amrap mailing.png");
        } catch (MessagingException ex) {
            Logger.getLogger(UsuarioImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
        } else {
            throw new ErrorServicio("Ya existe un usuario registrado con este email");
        }
    }

    @Transactional
    @Override
    public void modificarUsuario(String id, String nombre, String apellido, String email, MultipartFile archivo, Role rol) throws ErrorServicio {

        validarDatos(nombre, apellido, email);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();

            if (usuarioRepositorio.buscarUsuarioPorEmail(email).equals(usuario) || usuarioRepositorio.buscarUsuarioPorEmail(email) == null) {

                usuario.setNombre(nombre);
                usuario.setApellido(apellido);
                usuario.setEmail(email);
                usuario.setRol(rol);

                if (archivo != null && !archivo.isEmpty()) {

                    Foto foto = fotoImplement.guardarFoto(archivo);
                    usuario.setFoto(foto);
                }

                usuarioRepositorio.save(usuario);

            } else {
                throw new ErrorServicio("El email ingresado, ya esta registrado por otro usuario.");
            }
        } else {
            throw new ErrorServicio("No se encontró el usuario");
        }
    }

    @Transactional
    @Override
    public Usuario buscarUsuarioPorId(String id) throws ErrorServicio {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent() && respuesta != null) {

            Usuario usuario = respuesta.get();

            return usuario;

        } else {
            throw new ErrorServicio("No se encontro ningún usuario");
        }
    }

    @Override
    public List<Usuario> buscarUsuarioPorNombre(String nombre) throws ErrorServicio {

        List<Usuario> usuarios = usuarioRepositorio.buscarUsuarioPorNombre(nombre);

        if (usuarios != null || !usuarios.isEmpty()) {

            return usuarios;

        } else {
            throw new ErrorServicio("No se encontró ninguna usuario.");
        }
    }

    @Transactional
    @Override
    public Usuario buscarUsuarioPorEmail(String email) throws ErrorServicio {

        return usuarioRepositorio.buscarUsuarioPorEmail(email);

    }

    @Transactional
    @Override
    public void deshabilitarUsuario(String id) throws ErrorServicio {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();

            usuario.setBaja(new Date());

            usuarioRepositorio.save(usuario);

        } else {
            throw new ErrorServicio("No se encontró el usuario");
        }
    }

    @Transactional
    @Override
    public void habilitarUsuario(String id) throws ErrorServicio {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();

            usuario.setBaja(null);

            usuarioRepositorio.save(usuario);

        } else {
            throw new ErrorServicio("No se encontró el usuario");
        }
    }

    @Override
    public void modificarClaveUsuario(String id, String clave1, String clave2) throws ErrorServicio {

        validarClaves(clave1, clave2);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();

            String claveEncriptada = new BCryptPasswordEncoder().encode(clave1);
            usuario.setClave(claveEncriptada);

            usuarioRepositorio.save(usuario);

        } else {
            throw new ErrorServicio("No se encontró el usuario");
        }
    }

    @Override
    public void validarDatos(String nombre, String apellido, String email) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre, no puede ser nulo.");
        }

        if (apellido == null || apellido.isEmpty()) {
            throw new ErrorServicio("El apellido, no  puede ser nulo.");
        }

        if (email == null || email.isEmpty()) {
            throw new ErrorServicio("El mail no puede ser nulo.");
        }
    }

    @Override
    public void validarClaves(String clave1, String clave2) throws ErrorServicio {

        if (clave1 == null || clave1.isEmpty() || clave1.length() < 6) {
            throw new ErrorServicio("La clave no puede ser nula, y tiene que tener 6 o más caracteres.");
        }

        if (!clave1.equals(clave2)) {
            throw new ErrorServicio("Las claves deben ser iguales");
        }
    }

    public List<Usuario> listarUsuarios() throws ErrorServicio {

        List<Usuario> usuarios = usuarioRepositorio.findAllOrdenado();

        if (usuarios != null && !usuarios.isEmpty()) {

            return usuarios;

        } else {
            throw new ErrorServicio("No se encontro ningún usuario");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarUsuarioPorEmail(email);

        System.out.println("******************************* este es el usuario que encontró para cuando quiera logearme: " + usuario);

        if (usuario != null && usuario.getBaja() == null) {

            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority permiso1 = new SimpleGrantedAuthority("ROLE_" + usuario.getRol());
            permisos.add(permiso1);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);

            User user = new User(usuario.getEmail(), usuario.getClave(), permisos);
            return user;
        } else {
            return null;
        }
    }

}
