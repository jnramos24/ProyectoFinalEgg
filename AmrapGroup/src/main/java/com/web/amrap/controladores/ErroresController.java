package com.web.amrap.controladores;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class ErroresController implements ErrorController {

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
    public String mostrarPaginaDeError(ModelMap modelo, HttpServletRequest httpServletRequest) {
        String mensajeError = "";
        int codigoError = (int) httpServletRequest.getAttribute("javax.servlet.error.status_code");

        switch (codigoError) {
            case 400:
                mensajeError = "El recurso solicitado no existe";
                break;
            case 401:
                mensajeError = "No se encuentra autorizado";
                break;
            case 403:
                mensajeError = "No tiene permisos para acceder al recurso";
                break;
            case 404:
                mensajeError = "No se encuentra el recurso solicitado";
                break;
            case 500:
                mensajeError = "El servidor no pudo procesar su solicitud";
                break;
            default:

        }
        modelo.put("mensajeError", mensajeError);
        modelo.put("codigoError", codigoError);
        
        System.out.println("mensajeError: " + mensajeError);
        System.out.println("codigoError: " + codigoError);
        return "error.html";
    }

}
