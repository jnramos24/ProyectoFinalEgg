package com.web.amrap.repositorios;

import com.web.amrap.entidades.Rutina;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RutinaRepositorio extends JpaRepository<Rutina, String> {

    @Query("SELECT c FROM Rutina c WHERE c.usuario.id = :idUsuario")
    public List<Rutina> buscarRutinaPorUsuario(@Param("idUsuario") String idUsuario);

}
