//package com.example.demo.security;
//
//import com.example.demo.model.Role;
//import com.example.demo.model.User;
//import lombok.Data;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Optional;
//
//
//public class UserDetailsImpl implements org.springframework.security.core.userdetails.UserDetails {
//    private User user;
//
//    public UserDetailsImpl(Optional<User> user) {
//        this.user = user.get();
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<Role> roles = user.getRoles();
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//
//        for (Role role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role.getRole()));
//        }
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getName();
//    }
//
//}
