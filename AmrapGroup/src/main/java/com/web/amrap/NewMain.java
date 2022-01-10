/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.amrap;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author manue
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        String claveEncriptada = new BCryptPasswordEncoder().encode("1234");
            System.out.println("claveEncriptada: "+ claveEncriptada);  //esta clase la cree solo para poder entrar al sitio web
        
    }
    
}
