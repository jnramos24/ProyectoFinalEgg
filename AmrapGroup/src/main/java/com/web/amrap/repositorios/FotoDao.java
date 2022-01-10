package com.web.amrap.repositorios;

import com.web.amrap.entidades.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoDao extends JpaRepository<Foto, String> {

    @Query("SELECT c FROM Foto c WHERE c.id = :id")
    public Foto buscarFotoPorId(@Param("id") String id);
}
