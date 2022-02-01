package com.web.amrap.repositorios;

import com.web.amrap.entidades.IdentificadorEjerc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentificadorEjercRepositorio extends JpaRepository<IdentificadorEjerc, String> {

    @Query("SELECT MAX(c.id) FROM IdentificadorEjerc c ORDER BY c.id desc")
    public Integer traerUltimoIdentificador();
   

}
