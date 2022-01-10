package com.web.amrap.repositorios;

import com.web.amrap.entidades.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepositorio extends JpaRepository<Categoria, String> {

    @Query("SELECT c FROM Categoria c WHERE c.nombre like %:nombre%")
    public List<Categoria> buscarPorNombre(@Param("nombre") String nombre);
}
