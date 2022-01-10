package com.web.amrap;

import com.web.amrap.implementacion.UsuarioImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AmrapApplication {

    @Autowired
    private UsuarioImplement usuarioImplement;

    public static void main(String[] args) {
        SpringApplication.run(AmrapApplication.class, args);
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService (usuarioImplement).passwordEncoder(new BCryptPasswordEncoder());
    }

}
