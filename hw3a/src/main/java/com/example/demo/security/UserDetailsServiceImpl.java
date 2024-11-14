//package com.example.demo.security;
//
//import com.example.demo.model.User;
//import com.example.demo.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//@Service
//public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {
//    @Autowired
//    UserRepository usersRepository;
//    @Autowired
//    PasswordEncoder passwordEncoder;
//    public UserDetailsServiceImpl(UserRepository userRepository) {
//        this.usersRepository = userRepository;
//    }
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//       Optional<User> user = usersRepository.findByName(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return new UserDetailsImpl(user);
//    }
////    @Override
////    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////        User user = usersRepository.findByName(username)
////                .orElseThrow(() -> new UsernameNotFoundException(username));
////
////
////        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), List.of(
////                new SimpleGrantedAuthority(user.getRoles().get(0).getRole())
////        ));
////    }
//
//}
