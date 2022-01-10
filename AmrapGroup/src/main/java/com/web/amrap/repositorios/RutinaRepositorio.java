
package com.web.amrap.repositorios;

import com.web.amrap.entidades.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RutinaRepositorio extends JpaRepository<Rutina, String>{    
    
}
