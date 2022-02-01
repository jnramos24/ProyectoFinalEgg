package com.web.amrap.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviaMail(String cuerpo,
                                        String titulo,
                                        String mail,
                                        String archivo) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("amrapgroup22@gmail.com");
        mimeMessageHelper.setTo(mail);
        mimeMessageHelper.setText(cuerpo);
        mimeMessageHelper.setSubject(titulo);

        FileSystemResource fileSystem
                = new FileSystemResource(new File(archivo));

        mimeMessageHelper.addAttachment(fileSystem.getFilename(),
                fileSystem);

        mailSender.send(mimeMessage);
    }
}
