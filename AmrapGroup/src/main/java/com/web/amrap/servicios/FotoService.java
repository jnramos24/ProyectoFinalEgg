package com.web.amrap.servicios;

import com.web.amrap.entidades.Foto;
import org.springframework.web.multipart.MultipartFile;

public interface FotoService {

    public Foto guardarFoto(MultipartFile archivo);

    public void actualizarFoto(String idFoto, MultipartFile archivo);

    public Foto buscarFoto(String idFoto);
}
