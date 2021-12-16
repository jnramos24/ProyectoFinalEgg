package com.web.amrap.repositorios;

import com.web.amrap.entidades.Ejercicio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EjercicioRepositorio extends JpaRepository<Ejercicio, String> {

    @Query("SELECT c FROM Ejercicio c WHERE c.nombre like %:nombre%")
    public List<Ejercicio> buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT c FROM Ejercicio c WHERE c.categoria like %:categoria%")
    public List<Ejercicio> buscarPorCategoria(@Param("categoria") String categoria);
}
