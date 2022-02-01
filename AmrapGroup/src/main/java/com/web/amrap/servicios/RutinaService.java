
package com.web.amrap.servicios;

import com.web.amrap.entidades.Rutina;
import com.web.amrap.errores.ErrorServicio;
import java.util.List;
import org.springframework.stereotype.Service;


@Service
public interface RutinaService {
    
    public void crearRutina(String idUsuario,String nombre,String objetivo) throws ErrorServicio;
    
    public void modificarRutina (String idUsuario,String id,String nombre, String objetivo) throws ErrorServicio;    
   
    public void eliminarRutina(String idUsuario,String idRutina) throws ErrorServicio;
    
    public List<Rutina> listarRutinas() throws ErrorServicio;
    
    public Rutina buscarRutinaPorId(String id) throws ErrorServicio;   
    
    public List<Rutina> buscarRutinaPorUsuario(String idUsuario) throws ErrorServicio;
}
