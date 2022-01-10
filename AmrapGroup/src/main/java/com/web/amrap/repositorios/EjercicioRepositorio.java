package com.web.amrap.repositorios;

import com.web.amrap.entidades.Ejercicio;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EjercicioRepositorio extends JpaRepository<Ejercicio, String> {

    
    @Query("SELECT c FROM Ejercicio c WHERE c.ejercicioNombre.id like %:idNombre%")
    public List<Ejercicio> buscarPorNombre(@Param("idNombre") String idNombre);

    @Query("SELECT c FROM Ejercicio c WHERE c.ejercicioNombre.categoria.id like %:idCategoria%")
    public List<Ejercicio> buscarPorCategoria(@Param("idCategoria") String idCategoria);
    
    @Query("SELECT c FROM Ejercicio c WHERE c.identificador like :identificador")
    public Optional<Ejercicio> buscarPorIdentificador(@Param("identificador") String identificador);
    
     @Query("SELECT c FROM Ejercicio c WHERE c.rutina.id like %:idRutina%")
    public List<Ejercicio> buscarPorRutina(@Param("idRutina") String idRutina);
    

}
