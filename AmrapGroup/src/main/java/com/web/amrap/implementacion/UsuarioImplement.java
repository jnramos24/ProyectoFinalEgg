package com.web.amrap.implementacion;

import com.web.amrap.entidades.Usuario;
import com.web.amrap.errores.ErrorServicio;
import com.web.amrap.repositorios.UsuarioRepositorio;
import com.web.amrap.servicios.UsuarioService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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

    @Transactional
    @Override
    public void registarUsuario(String nombre, String apellido, String dni, String email, String clave1, String clave2, MultipartFile archivo) throws ErrorServicio {

        validarDatos(nombre, apellido, email, dni);
        validarClaves(clave1, clave2);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setDni(dni);
        usuario.setEmail(email);
        usuario.setAlta(new Date());

        String claveEncriptada = new BCryptPasswordEncoder().encode(clave1);
        usuario.setClave(claveEncriptada);

//        Foto foto = fotoServicio.guardar(archivo);
//        usuario.setFoto(foto);
        usuarioRepositorio.save(usuario);
    }

    @Transactional
    @Override
    public void modificarUsuario(String id, String nombre, String apellido, String dni, String email, MultipartFile archivo) throws ErrorServicio {

        validarDatos(nombre, apellido, email, dni);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();

            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setEmail(email);
            usuario.setDni(dni);



//            String idFoto = null;
//
//            if (usuario.getFoto() != null) {
//                idFoto = usuario.getFoto().getId();
//            }
//            Foto foto = fotoServicio.actualizarFoto(idFoto, archivo);
//            usuario.setFoto(foto);
            usuarioRepositorio.save(usuario);

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
    public void validarDatos(String nombre, String apellido, String email, String dni) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre, no puede ser nulo.");
        }

        if (apellido == null || apellido.isEmpty()) {
            throw new ErrorServicio("El apellido, no  puede ser nulo.");
        }

        if (email == null || email.isEmpty()) {
            throw new ErrorServicio("El mail no puede ser nulo.");
        }

        if (dni == null || dni.isEmpty()) {
            throw new ErrorServicio("El dni no puede ser nulo.");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarUsuarioPorMail(email);

        System.out.println("******************************* este es el usuario que encontró para cuando quiera logearme: " + usuario);

        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority permiso1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
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

    //******************************
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
    public void validarClaves(String clave1, String clave2) throws ErrorServicio {

        if (clave1 == null || clave1.isEmpty() || clave1.length() < 6) {
            throw new ErrorServicio("La clave no puede ser nula, y tiene que tener 6 o más caracteres.");
        }

        if (!clave1.equals(clave2)) {
            throw new ErrorServicio("Las claves deben ser iguales");
        }
    }

}
