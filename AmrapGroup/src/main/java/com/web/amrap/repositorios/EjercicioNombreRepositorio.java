package com.web.amrap.repositorios;

import com.web.amrap.entidades.EjercicioNombre;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EjercicioNombreRepositorio extends JpaRepository<EjercicioNombre, String> {

    @Query("SELECT c FROM EjercicioNombre c WHERE c.nombre like %:nombre%")
    public List<EjercicioNombre> buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT c FROM EjercicioNombre c WHERE c.nombre =:nombre")
    EjercicioNombre buscarPorNombreEjercicio(@Param("nombre") String nombre);
    
    @Query("SELECT c FROM EjercicioNombre c WHERE c.categoria.id like %:idCategoria%")
    public List<EjercicioNombre> buscarPorCategoria(@Param("idCategoria") String idCategoria );

}
