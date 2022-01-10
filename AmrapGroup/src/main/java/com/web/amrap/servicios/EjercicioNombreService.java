package com.web.amrap.servicios;

import com.web.amrap.entidades.EjercicioNombre;
import com.web.amrap.errores.ErrorServicio;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface EjercicioNombreService {

    public void crearEjercicioNombre(String nombre,String video, String idCategoria) throws ErrorServicio;

    public void modificarEjercicioNombre(String id, String nombre, String video, String idCategoria) throws ErrorServicio;

    public void eliminarEjercicioNombre(String id) throws ErrorServicio;

    public EjercicioNombre buscarEjercicioNombrePorId(String id) throws ErrorServicio;

    public List<EjercicioNombre> buscarPorNombre(String nombre) throws ErrorServicio;
    
    public List<EjercicioNombre> buscarPorCategoria(String idCategoria) throws ErrorServicio;

    public void validarDatos(String nombre) throws ErrorServicio;
    
    public List<EjercicioNombre> listarEjercicioNombre() throws ErrorServicio;

}
