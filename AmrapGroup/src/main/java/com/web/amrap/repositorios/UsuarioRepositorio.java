package com.web.amrap.repositorios;

import com.web.amrap.entidades.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("SELECT c FROM Usuario c WHERE c.email = :email" )
    public Usuario buscarUsuarioPorEmail(@Param("email") String email);
    
    
    @Query("SELECT c FROM Usuario c ORDER BY c.alta desc " )
    public List<Usuario> findAllOrdenado();
    
    @Query("SELECT c FROM Usuario c WHERE c.nombre like %:nombre% OR c.apellido like %:nombre% ORDER BY c.alta desc")
    public List<Usuario> buscarUsuarioPorNombre(@Param("nombre") String nombre);

}
