//package com.example.demo.security;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;

//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
//public class SecurityConfiguration {

//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//        converter.setJwtGrantedAuthoritiesConverter(source -> {
//            Map<String, Object> resourceAccess = source.getClaim("realm_access");
//            List<String> roles = (List<String>) resourceAccess.get("roles");
//
//            return roles.stream()
//                    .map(SimpleGrantedAuthority::new)
//                    .collect(Collectors.toList());
//        });
//
//        return httpSecurity
//                .authorizeHttpRequests(configurer -> configurer
//                        .requestMatchers("/ui/issues/**").hasAuthority("admin")
//                        .requestMatchers("/ui/readers/**").hasAuthority("reader")
//                        .requestMatchers("/ui/books/**").authenticated()
//                        .requestMatchers("/ui/login").permitAll()
//
//                        .anyRequest().denyAll()
//
//                )
//                //.formLogin((form) -> form.defaultSuccessUrl("/ui/books").permitAll())
//      .formLogin(Customizer.withDefaults())
////                .oauth2ResourceServer(configurer -> configurer
////                        .jwt(jwtConfigurer -> jwtConfigurer
////                                .jwtAuthenticationConverter(converter))
////                )
//                .build();
//    }
//}
