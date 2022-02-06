package com.web.amrap.controladores;

import com.web.amrap.implementacion.UsuarioImplement;
import com.web.amrap.servicios.MailService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class ContactoClienteController {

    @Autowired
    private MailService mailService;

    @PostMapping("/contactar")
    public String contactar(ModelMap modelo, MultipartFile archivo,
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String message) {

        try {

            mailService.enviaMail("nombre: " + fullName + "\n"
                    + "E-mail: " + email + "\n"
                    + "Telefono: " + phone + "\n"
                    + "Mensaje: " + message, "Contacto cliente",
                    "amrapgroup22@hotmail.com", "C:\\ProyectoFinalEgg\\AmrapGroup\\src\\main\\resources\\static\\img\\Amrap mailing.png");

        } catch (MessagingException ex) {
            Logger.getLogger(UsuarioImplement.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "index.html";
    }

}
