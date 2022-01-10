package com.web.amrap.servicios;

import com.web.amrap.entidades.Ejercicio;
import com.web.amrap.entidades.EjercicioNombre;
import com.web.amrap.entidades.Rutina;
import com.web.amrap.errores.ErrorServicio;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface EjercicioService {

    public Ejercicio crearEjercicio(Integer series, Integer repeticiones,Integer pausa, 
            Integer dificultad, String kilogramos, String notas, String atencion, String idEjercNom, String idRutina) throws ErrorServicio;

    public void modificarEjercicio(String id, Integer series, Integer repeticiones, Integer pausa, 
            Integer dificultad,String kilogramos, String notas, String atencion, String idEjercNom, String idRutina) throws ErrorServicio;

    public void eliminarEjercicio(String id) throws ErrorServicio;

    public Ejercicio buscarEjercicioPorId(String id) throws ErrorServicio;

    public List<Ejercicio> buscarPorNombre(String nombre) throws ErrorServicio;

    public List<Ejercicio> buscarPorCategoria(String categoria) throws ErrorServicio;
    
    public List<Ejercicio> listarPorRutina(String categoria) throws ErrorServicio;
    
    public Ejercicio buscarEjercicioPorIdentificador(String id) throws ErrorServicio;
    
    public void validarDatos(Rutina rutina, EjercicioNombre ejercicioNombre, Integer series, Integer repeticiones,
            Integer pausa, Integer dificultad, String kilogramos) throws ErrorServicio;

    public List<Ejercicio> listarEjercicios() throws ErrorServicio;

}
