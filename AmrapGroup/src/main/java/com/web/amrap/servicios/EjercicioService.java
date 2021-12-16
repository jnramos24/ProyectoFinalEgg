
package com.web.amrap.servicios;

import com.web.amrap.entidades.Ejercicio;
import com.web.amrap.errores.ErrorServicio;
import static java.util.Collections.list;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface EjercicioService {
    
    public void crearEjercicio(String nombre, String categoria, String video,Integer series, Integer repeticiones, 
            Integer pausa, Integer dificultad, String kilogramos, String notas, String atencion) throws ErrorServicio;
    
    public void modificarEjercicio (String id, String nombre, String categoria, String video, Integer series, 
            Integer repeticiones, Integer pausa, Integer dificultad, String kilogramos, String notas, String atencion) throws ErrorServicio;    
   
    public void eliminarEjercicio(String id) throws ErrorServicio;
    
    public Ejercicio buscarEjercicioPorId(String id) throws ErrorServicio;
    
    public List<Ejercicio> buscarPorNombre(String nombre)throws ErrorServicio;
    
    public List<Ejercicio> buscarPorCategoria(String categoria)throws ErrorServicio;
    
    public void validarDatos(String nombre, String categoria, String video, Integer series, Integer repeticiones, 
            Integer pausa, Integer dificultad, String kilogramos) throws ErrorServicio;     

    
}
 