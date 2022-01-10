package com.web.amrap.implementacion;

import com.web.amrap.entidades.Ejercicio;
import com.web.amrap.entidades.Rutina;
import com.web.amrap.entidades.Usuario;
import com.web.amrap.errores.ErrorServicio;
import com.web.amrap.repositorios.RutinaRepositorio;
import com.web.amrap.repositorios.UsuarioRepositorio;
import com.web.amrap.servicios.RutinaService;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RutinaImplement implements RutinaService {

    @Autowired
    RutinaRepositorio rutinaRepositorio;

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Transactional
    @Override
    public void crearRutina(String idUsuario, String nombre, String objetivo) throws ErrorServicio {

        validarDatos(nombre);

        Usuario usuario = usuarioRepositorio.findById(idUsuario).get();

        Rutina rutina = new Rutina();

        rutina.setNombre(nombre);
        rutina.setObjetivo(objetivo);
        rutina.setUsuario(usuario);

        rutinaRepositorio.save(rutina);
    }

    @Transactional
    @Override
    public void modificarRutina(String idUsuario, String idRutina, String nombre, String objetivo) throws ErrorServicio {

        validarDatos(nombre);

        Optional<Rutina> respuesta = rutinaRepositorio.findById(idRutina);

        if (respuesta.isPresent()) {

            Rutina rutina = respuesta.get();

            if (rutina.getUsuario().getId().equals(idUsuario)) {
                
                rutina.setNombre(nombre);
                rutina.setObjetivo(objetivo);
                
                 rutinaRepositorio.save(rutina);
            } else {
                throw new ErrorServicio("El usuario no tiene permisos suficientes para realizar la modificación");
            }

           
        } else {
            throw new ErrorServicio("No se encontró ninguna rutina con este id.");
        }
    }

    @Transactional
    @Override
    public void eliminarRutina(String idUsuario,String idRutina) throws ErrorServicio {

        Optional<Rutina> respuesta = rutinaRepositorio.findById(idRutina);

        if (respuesta.isPresent()) {
            
            if (respuesta.get().getUsuario().getId().equals(idUsuario)) {
                       
                 rutinaRepositorio.deleteById(idRutina);
                 
            } else {
                throw new ErrorServicio("El usuario no tiene permisos suficientes para realizar la modificación");
            }

        } else {
            throw new ErrorServicio("No se encontró ninguna rutina con este id.");
        }
    }

    @Override
    public List<Rutina> listarRutinas() throws ErrorServicio {

        List<Rutina> rutinas = rutinaRepositorio.findAll();

        if (rutinas != null && !rutinas.isEmpty()) {

            return rutinas;

        } else {
            throw new ErrorServicio("No se encontró ningun ejercicio.");
        }
    }

    @Transactional
    @Override
    public Rutina buscarRutinaPorId(String id) throws ErrorServicio {

        Optional<Rutina> respuesta = rutinaRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Rutina rutina = respuesta.get();

            return rutina;

        } else {
            throw new ErrorServicio("No se encontró ninguna rutina con este id.");
        }
    }

    public void validarDatos(String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre, no puede ser nulo.");
        }
    }
}
