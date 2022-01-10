package com.web.amrap.implementacion;

import com.web.amrap.entidades.Foto;
import com.web.amrap.repositorios.FotoDao;
import org.springframework.stereotype.Service;
import com.web.amrap.servicios.FotoService;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoImplement implements FotoService {

    @Autowired
    private FotoDao fotoDao;

    @Transactional
    @Override
    public Foto guardarFoto(MultipartFile archivo) {
        
        if (archivo != null) {
            try {
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
                return fotoDao.save(foto);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    @Transactional
    @Override
    public void actualizarFoto(String idFoto, MultipartFile archivo) {

        if (archivo != null) {
            try {
                Foto foto = new Foto();

                if (idFoto != null) {
                    Optional<Foto> respuesta = fotoDao.findById(idFoto);
                    if (respuesta.isPresent()) {
                        foto = respuesta.get();
                    }
                }

                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                fotoDao.save(foto);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        fotoDao.save(null);
    }

    @Override
    public Foto buscarFoto(String idFoto) {

        Foto foto = new Foto();
        if (idFoto != null) {
            Optional<Foto> respuesta = fotoDao.findById(idFoto);
            if (respuesta.isPresent()) {
                foto = respuesta.get();

            }
        }
        return foto;
    }

}
