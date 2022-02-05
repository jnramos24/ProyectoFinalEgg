package com.web.amrap.implementacion;

import Enumeraciones.Role;
import com.web.amrap.entidades.Ejercicio;
import com.web.amrap.entidades.EjercicioNombre;
import com.web.amrap.entidades.Rutina;
import com.web.amrap.errores.ErrorServicio;
import com.web.amrap.repositorios.EjercicioNombreRepositorio;
import com.web.amrap.repositorios.EjercicioRepositorio;
import com.web.amrap.servicios.EjercicioService;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.web.amrap.repositorios.RutinaRepositorio;
import java.util.Date;

@Service
public class EjercicioImplement implements EjercicioService {

    @Autowired
    EjercicioRepositorio ejercicioRepositorio;

    @Autowired
    EjercicioNombreRepositorio ejercicioNombreRepositorio;

    @Autowired
    IdentificadorImplement identificadorImplement;

    @Autowired
    RutinaRepositorio rutinaRepositorio;

    @Transactional
    @Override
    public Ejercicio crearEjercicio(Integer series, Integer repeticiones, Integer pausa, Integer dificultad,
            String kilogramos, String notas, String atencion, String idEjercNom, String idRutina) throws ErrorServicio {

        EjercicioNombre ejercicioNombre = ejercicioNombreRepositorio.findById(idEjercNom).get();

        Rutina rutina = rutinaRepositorio.findById(idRutina).get();

        validarDatos(rutina, ejercicioNombre, series, repeticiones, pausa);

        String identificador = identificadorImplement.indentificador(); // con este identificador, puedo buscar el ejercicio que acabo de crear.

        Ejercicio ejercicio = new Ejercicio();

        ejercicio.setEjercicioNombre(ejercicioNombre);
        ejercicio.setRutina(rutina);
        ejercicio.setSeries(series);
        ejercicio.setRepeticiones(repeticiones);
        ejercicio.setPausa(pausa);
        ejercicio.setDificultad(dificultad);
        ejercicio.setKilogramos(kilogramos);
        ejercicio.setNotas(notas);
        ejercicio.setAtencion(atencion);
        ejercicio.setIdentificador(identificador);
        ejercicio.setCompletado(false);
        ejercicio.setAlta(new Date());

        ejercicioRepositorio.save(ejercicio);

        ejercicio = ejercicioRepositorio.buscarPorIdentificador(identificador).get();

        return ejercicio;
    }

    @Transactional
    @Override
     public void modificarEjercicio(String id, Integer series, Integer repeticiones, Integer pausa, Integer dificultad,
            String kilogramos, String notas, String atencion, String idEjercNom, String idRutina, Boolean completado) throws ErrorServicio {

        EjercicioNombre ejercicioNombre = ejercicioNombreRepositorio.findById(idEjercNom).get();

        Rutina rutina = rutinaRepositorio.findById(idRutina).get();

        validarDatos(rutina, ejercicioNombre, series, repeticiones, pausa);

        Optional<Ejercicio> respuestaEjercicio = ejercicioRepositorio.findById(id);

        if (rutina.getUsuario().getRol().equals(Role.USUARIO_REGISTRADO) && respuestaEjercicio.isPresent()) {

            Ejercicio ejercicio = respuestaEjercicio.get();

            ejercicio.setDificultad(dificultad);
            ejercicio.setKilogramos(kilogramos);
            ejercicio.setNotas(notas);
            ejercicio.setAtencion(atencion);
            ejercicio.setCompletado(true);

            ejercicioRepositorio.save(ejercicio);

        } else if (rutina.getUsuario().getRol().equals(Role.ADMIN) && respuestaEjercicio.isPresent()) {

            Ejercicio ejercicio = respuestaEjercicio.get();

            ejercicio.setEjercicioNombre(ejercicioNombre);
            ejercicio.setRutina(rutina);
            ejercicio.setSeries(series);
            ejercicio.setRepeticiones(repeticiones);
            ejercicio.setPausa(pausa);
            ejercicio.setDificultad(dificultad);
            ejercicio.setKilogramos(kilogramos);
            ejercicio.setNotas(notas);
            ejercicio.setAtencion(atencion);
            ejercicio.setCompletado(true);

            ejercicioRepositorio.save(ejercicio);

        } else {
            throw new ErrorServicio("No se encontró ningun ejercicio con este id.");
        }
    }

    @Transactional
    @Override
    public void eliminarEjercicio(String id) throws ErrorServicio {

        Optional<Ejercicio> respuestaEjercicio = ejercicioRepositorio.findById(id);

        if (respuestaEjercicio.isPresent()) {

            ejercicioRepositorio.deleteById(id);

        } else {
            throw new ErrorServicio("No se encontró ningun ejercicio con este id.");
        }

    }

    @Override
    public List<Ejercicio> listarEjercicios() throws ErrorServicio {

        List<Ejercicio> ejercicios = ejercicioRepositorio.findAll();

        if (ejercicios != null && !ejercicios.isEmpty()) {

            return ejercicios;

        } else {
            throw new ErrorServicio("No se encontró ningun ejercicio.");
        }
    }

    @Transactional
    @Override
    public Ejercicio buscarEjercicioPorId(String id) throws ErrorServicio {

        Optional<Ejercicio> respuesta = ejercicioRepositorio.findById(id);

        if (respuesta.isPresent() && respuesta != null) {

            Ejercicio ejercicio = respuesta.get();

            return ejercicio;

        } else {
            throw new ErrorServicio("No se encontro ningún ejercicio");
        }

    }

    @Transactional
    @Override
    public List<Ejercicio> buscarPorNombre(String nombre, String idRutina) throws ErrorServicio {

        List<Ejercicio> ejercicios = ejercicioRepositorio.buscarPorNombre(nombre, idRutina);

        if (ejercicios != null && !ejercicios.isEmpty()) {

            return ejercicios;

        } else {
            throw new ErrorServicio("No se encontro ningún ejercicio");
        }
    }

    @Transactional
    @Override
    public List<Ejercicio> buscarPorNombreEjercicio(String idEjerccioNombre) throws ErrorServicio {

        List<Ejercicio> ejercicios = ejercicioRepositorio.buscarPorNombreEjercicio(idEjerccioNombre);

        if (ejercicios != null && !ejercicios.isEmpty()) {

            return ejercicios;

        } else {
            throw new ErrorServicio("No se encontro ningún ejercicio");
        }
    }

    @Transactional
    @Override
    public List<Ejercicio> buscarPorCategoria(String cateogoria, String idRutina) throws ErrorServicio {

        List<Ejercicio> ejercicios = ejercicioRepositorio.buscarPorCategoria(cateogoria, idRutina);

        if (ejercicios != null && !ejercicios.isEmpty()) {

            return ejercicios;

        } else {
            throw new ErrorServicio("No se encontro ningún ejercicio");
        }
    }

    @Override
    public List<Ejercicio> listarPorRutina(String idRutina) throws ErrorServicio {

        List<Ejercicio> ejercicios = ejercicioRepositorio.buscarPorRutina(idRutina);

        Integer contador = 0;

        Rutina rutina = null;

        if (ejercicios != null && !ejercicios.isEmpty()) {

            for (Ejercicio ejercicio : ejercicios) {

                if (ejercicio.getCompletado() == true) {

                    contador++;
                }
            }

            if (ejercicios.size() == contador) {

                rutina = rutinaRepositorio.findById(idRutina).get();

                rutina.setCompletado(Boolean.TRUE);

                rutinaRepositorio.save(rutina);

            } else {
                
                rutina = rutinaRepositorio.findById(idRutina).get();
                
                rutina.setCompletado(Boolean.FALSE);                

                rutinaRepositorio.save(rutina);
            }

            return ejercicios;

        } else {
            throw new ErrorServicio("No se encontro ningún ejercicio");
        }
    }

    @Transactional
    @Override
    public Ejercicio buscarEjercicioPorIdentificador(String identificador) throws ErrorServicio {

        Optional<Ejercicio> respuesta = ejercicioRepositorio.buscarPorIdentificador(identificador);

        if (respuesta.isPresent() && respuesta != null) {

            Ejercicio ejercicio = respuesta.get();

            return ejercicio;

        } else {
            throw new ErrorServicio("No se encontro ningun ejercicio con este identificador.");
        }
    }

    @Override
    public void validarDatos(Rutina rutina, EjercicioNombre ejercicioNombre, Integer series, Integer repeticiones,
            Integer pausa) throws ErrorServicio {

        if (ejercicioNombre == null) {
            throw new ErrorServicio("El ejercicio debe contener un objeto EjercicioNombre.");
        }

        if (series == null) {
            throw new ErrorServicio("Las series, no pueden ser nulas.");
        }

        if (repeticiones == null) {
            throw new ErrorServicio("Las repeticiones, no pueden ser nulas.");
        }

        if (pausa == null) {
            throw new ErrorServicio("La pausa, no pueden ser nula.");
        }

    }

}
