package com.schoolofliberation.academic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeRequests(requests -> requests
                        .antMatchers("/webjars/**").permitAll() // Permitir acceso a los recursos de WebJars
                        .antMatchers("/editar/**", "/agregar/**", "/eliminar/**").hasRole("ADMIN")
                        .antMatchers("/").hasAnyRole("USER", "ADMIN")
                        .antMatchers("/anonymous*").anonymous())
                .formLogin(login -> login
                        .loginPage("/login"))
                .exceptionHandling(handling -> handling
                        .accessDeniedPage("/error/403"));

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
