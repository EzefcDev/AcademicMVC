package com.schoolofliberation.academic.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncrypterPass {
    
    public static void main(String[] args) {
        
        // String password = "paracontroldeapp";
        //String password = "casalinda";
        String password = "este123noPara!!!";
        System.out.println("password: " + encriptarPassword(password) );
    }
    
    public static String encriptarPassword(String password){
        BCryptPasswordEncoder enconder = new BCryptPasswordEncoder();
        return enconder.encode(password);
    }
}
