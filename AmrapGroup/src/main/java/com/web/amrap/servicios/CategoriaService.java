
package com.web.amrap.servicios;
import com.web.amrap.entidades.Categoria;
import com.web.amrap.errores.ErrorServicio;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface CategoriaService {
    
    public void crearCategoria(String nombre) throws ErrorServicio;
    
    public void modificarCategoria (String id, String nombre) throws ErrorServicio;    
   
    public void eliminarCategoria(String id) throws ErrorServicio;
    
    public Categoria buscarCategoriaPorId(String id) throws ErrorServicio;
    
    public List<Categoria> buscarPorNombre(String nombre)throws ErrorServicio;
    
    public void validarDatos(String nombre) throws ErrorServicio;     

    public List<Categoria> listarCategorias()throws ErrorServicio;
}
