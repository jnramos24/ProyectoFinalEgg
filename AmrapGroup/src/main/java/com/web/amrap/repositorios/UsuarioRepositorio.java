package com.web.amrap.repositorios;

import com.web.amrap.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("SELECT c FROM Usuario c WHERE c.email = :email")
    public Usuario buscarUsuarioPorMail(@Param("email") String email);

    @Query("SELECT c FROM Usuario c WHERE c.dni = :dni")
    public Usuario buscarPorDNI(@Param("dni") String dni);
}
