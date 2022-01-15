package com.web.amrap.implementacion;

import com.web.amrap.entidades.Categoria;
import com.web.amrap.entidades.Ejercicio;
import com.web.amrap.entidades.EjercicioNombre;
import com.web.amrap.errores.ErrorServicio;
import com.web.amrap.repositorios.CategoriaRepositorio;
import com.web.amrap.repositorios.EjercicioNombreRepositorio;
import com.web.amrap.repositorios.EjercicioRepositorio;
import com.web.amrap.servicios.EjercicioNombreService;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EjercicioNombreImplement implements EjercicioNombreService {

    @Autowired
    EjercicioNombreRepositorio ejercicioNombreRepositorio;

//    @Autowired
//    CategoriaImplement categoriaImplement;
    
    @Autowired
    CategoriaRepositorio categoriaRepositorio;
    
    @Autowired
    EjercicioRepositorio ejercicioRepositorio;
    

    @Transactional
    @Override
    public void crearEjercicioNombre(String nombre, String video, String idCategoria) throws ErrorServicio {

        validarDatos(nombre);

        EjercicioNombre ejercicioNombre = new EjercicioNombre();

        Categoria categoria = categoriaRepositorio.findById(idCategoria).get();

        ejercicioNombre.setNombre(nombre);
        ejercicioNombre.setVideo(video);
        ejercicioNombre.setCategoria(categoria);

        ejercicioNombreRepositorio.save(ejercicioNombre);
    }

    @Transactional
    @Override
    public void modificarEjercicioNombre(String id, String nombre, String video, String idCategoria) throws ErrorServicio {

        validarDatos(nombre);

        Optional<EjercicioNombre> respuestaEjercicioNombre = ejercicioNombreRepositorio.findById(id);

        Categoria categoria = categoriaRepositorio.findById(idCategoria).get();

        if (respuestaEjercicioNombre.isPresent() && categoria != null) {

            EjercicioNombre ejercicioNombre = respuestaEjercicioNombre.get();

            ejercicioNombre.setNombre(nombre);
            ejercicioNombre.setVideo(video);
            ejercicioNombre.setCategoria(categoria);

            ejercicioNombreRepositorio.save(ejercicioNombre);

        } else {
            throw new ErrorServicio("No se puedo modificar el EjercicioNombre ");
        }
    } 

    @Transactional
    @Override
    public void eliminarEjercicioNombre(String id) throws ErrorServicio {

        Optional<EjercicioNombre> respuestaEjercicioNombre = ejercicioNombreRepositorio.findById(id);
        
        List<Ejercicio> respuestaEjercicio = ejercicioRepositorio.buscarPorNombreEjercicio(id);

        if (respuestaEjercicioNombre.isPresent() && respuestaEjercicio.isEmpty() ) {

            ejercicioNombreRepositorio.deleteById(id);

        } else {
            throw new ErrorServicio("No se pudo eliminar el Nombre de ejercicio. Primero debe eliminar los ejercicios creados con este nombre");
        }
    }

    @Override
    public EjercicioNombre buscarEjercicioNombrePorId(String id) throws ErrorServicio {

        Optional<EjercicioNombre> respuesta = ejercicioNombreRepositorio.findById(id);

        if (respuesta.isPresent()) {

            EjercicioNombre ejercicioNombre = respuesta.get();

            return ejercicioNombre;

        } else {
            throw new ErrorServicio("No se encontró ningun ejercicio con este id.");
        }
    }

    @Override
    public List<EjercicioNombre> buscarPorNombre(String nombre) throws ErrorServicio {

        List<EjercicioNombre> ejerciciosNombres = ejercicioNombreRepositorio.buscarPorNombre(nombre);

        if (ejerciciosNombres != null || !ejerciciosNombres.isEmpty()) {

            return ejerciciosNombres;

        } else {
            throw new ErrorServicio("No se encontró ningun EjercicioNombre.");
        }
    }

    @Override
    public List<EjercicioNombre> buscarPorCategoria(String idCategoria) throws ErrorServicio {

        List<EjercicioNombre> ejerciciosNombres = ejercicioNombreRepositorio.buscarPorCategoria(idCategoria);

        if (ejerciciosNombres != null || !ejerciciosNombres.isEmpty()) {

            return ejerciciosNombres;

        } else {
            throw new ErrorServicio("No se encontró ningun EjercicioNombre.");
        }
    }

    @Override
    public List<EjercicioNombre> listarEjercicioNombre() throws ErrorServicio {

        List<EjercicioNombre> listaEjercicioNombre = ejercicioNombreRepositorio.findAll();

        if (listaEjercicioNombre != null || !listaEjercicioNombre.isEmpty()) {

            return listaEjercicioNombre;

        } else {
            throw new ErrorServicio("No se encontró ningun listaEjercicioNombre.");
        }
    }

    @Override
    public void validarDatos(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del ejercicio, no puede ser nulo!.");
        }
    }
}
