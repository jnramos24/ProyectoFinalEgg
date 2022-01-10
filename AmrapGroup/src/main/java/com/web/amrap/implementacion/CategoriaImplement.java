package com.web.amrap.implementacion;

import com.web.amrap.repositorios.CategoriaRepositorio;
import com.web.amrap.entidades.Categoria;
import com.web.amrap.entidades.EjercicioNombre;
import com.web.amrap.errores.ErrorServicio;
import com.web.amrap.repositorios.EjercicioNombreRepositorio;
import com.web.amrap.servicios.CategoriaService;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaImplement implements CategoriaService {

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Autowired
    private EjercicioNombreRepositorio ejercicioNombreRepositorio;//

    @Transactional
    @Override
    public void crearCategoria(String nombre) throws ErrorServicio {

        validarDatos(nombre);

        Categoria categoria = new Categoria();

        categoria.setNombre(nombre);

        categoriaRepositorio.save(categoria);
    }

    @Transactional
    @Override
    public void modificarCategoria(String id, String nombre) throws ErrorServicio {

        validarDatos(nombre);

        Optional<Categoria> respuestaCategoria = categoriaRepositorio.findById(id);

        if (respuestaCategoria.isPresent()) {

            Categoria categoria = respuestaCategoria.get();

            categoria.setNombre(nombre);

            categoriaRepositorio.save(categoria);

        } else {
            throw new ErrorServicio("No se pudo modificar la categoría.");
        }
    }

    @Transactional
    @Override
    public void eliminarCategoria(String id) throws ErrorServicio {

        Optional<Categoria> respuestaCategoria = categoriaRepositorio.findById(id);

        List<EjercicioNombre> ejerciciosNombres = ejercicioNombreRepositorio.buscarPorCategoria(id);

        if (respuestaCategoria.isPresent() && ejerciciosNombres.isEmpty()) {

            categoriaRepositorio.deleteById(id);

        } else {
            throw new ErrorServicio("No se pudo eliminar la categoría. Primero debe eliminar los nombres de ejercicios asociados a la categoría");
        }
    }

    @Transactional
    @Override
    public Categoria buscarCategoriaPorId(String id) throws ErrorServicio {

        Optional<Categoria> respuesta = categoriaRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Categoria categoria = respuesta.get();

            return categoria;

        } else {
            throw new ErrorServicio("No se encontró ningun ejercicio con este id.");
        }
    }

    @Override
    public List<Categoria> buscarPorNombre(String nombre) throws ErrorServicio {

        List<Categoria> categoria = categoriaRepositorio.buscarPorNombre(nombre);

        if (categoria != null || !categoria.isEmpty()) {

            return categoria;

        } else {
            throw new ErrorServicio("No se encontró ninguna categoría.");
        }
    }

    @Override
    public List<Categoria> listarCategorias() throws ErrorServicio {

        List<Categoria> categoria = categoriaRepositorio.findAll();

        if (categoria != null || !categoria.isEmpty()) {

            return categoria;

        } else {
            throw new ErrorServicio("No se encontró ninguna categoría.");
        }
    }

    @Override
    public void validarDatos(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre de la categoría, no puede ser nulo!");
        }
    }

    public void guardarCategoria(Categoria categoria) throws ErrorServicio {

        if (categoria != null) {

            categoriaRepositorio.save(categoria);

        } else {
            throw new ErrorServicio("No se encontró ninguna categoría.");
        }

    }
}
