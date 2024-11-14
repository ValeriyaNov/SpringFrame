//package com.example.demo.security;
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserPasswordEncoder implements PasswordEncoder {
//    @Override
//    public String encode(CharSequence rawPassword) {
//        // шифруем пароль
//        return String.valueOf(rawPassword);
//    }
//
//    @Override
//    public boolean matches(CharSequence rawPassword, String encodedPassword) {
//        return encode(rawPassword).equals(encodedPassword);
//    }
//}
