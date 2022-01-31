package com.web.amrap.servicios;

import Enumeraciones.Role;
import com.web.amrap.entidades.Usuario;
import com.web.amrap.errores.ErrorServicio;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UsuarioService {

    public void registarUsuario(String nombre, String apellido, String mail, String clave1, String clave2, MultipartFile archivo) throws ErrorServicio;

    public void modificarUsuario(String id,String nombre, String apellido, String mail, MultipartFile archivo, Role rol) throws ErrorServicio;

    public Usuario buscarUsuarioPorId(String id) throws ErrorServicio;
    
    public Usuario buscarUsuarioPorEmail(String email) throws ErrorServicio;
    
    public void deshabilitarUsuario(String id) throws ErrorServicio;

    public void habilitarUsuario(String id) throws ErrorServicio;

    public void validarDatos(String nombre, String apellido, String mail) throws ErrorServicio;
    
    public void modificarClaveUsuario(String id,  String clave1, String clave2) throws ErrorServicio;
    
    public void validarClaves( String clave1, String clave2) throws ErrorServicio;
    
    public List<Usuario> buscarUsuarioPorNombre(String nombre) throws ErrorServicio;

}


