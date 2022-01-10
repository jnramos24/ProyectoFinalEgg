package com.web.amrap.implementacion;

import com.web.amrap.entidades.IdentificadorEjerc;
import com.web.amrap.repositorios.IdentificadorEjercRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdentificadorImplement {

    @Autowired
    IdentificadorEjercRepositorio identificadorEjercRepositorio;

    public String indentificador() {

        IdentificadorEjerc identificador = new IdentificadorEjerc();

        identificadorEjercRepositorio.save(identificador);

        Integer identificadorId = identificadorEjercRepositorio.traerUltimoIdentificador();

        String idUltimo = identificadorId.toString();

        String caracter1 = segun();
        String caracter2 = segun();
        String caracter3 = segun();      

        String identificadorUnico = identificadorId + caracter1 + caracter2 + caracter3;

        System.out.println("Este es el identificador unico que se creo: " + identificadorUnico);
        return identificadorUnico;
    }

    private String segun() {

        String caracter = "";

        int numero = (int) (Math.random() * (5 - 1 + 1) + 1);

        switch (numero) {
            case 1:
                caracter = "A";
                break;
            case 2:
                caracter = "B";
                break;
            case 3:
                caracter = "C";
                break;
            case 4:
                caracter = "D";
                break;
            case 5:
                caracter = "E";
                break;
            default:
                break;
        }

        return caracter;
    }

}
