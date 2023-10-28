package com.schoolofliberation.academic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public InMemoryUserDetailsManager userDetailsService(){
        UserDetails user1 = User.withUsername("Juan").password(passwordEncoder().encode("casaLinda1")).roles("USER").build();
        UserDetails user2 = User.withUsername("Sonia").password(passwordEncoder().encode("ventanasrosas")).roles("USER").build();
        UserDetails user3 = User.withUsername("BabyDev").password(passwordEncoder().encode("pruebascontrol")).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user1,user2,user3);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/webjars/**").permitAll() // Permitir acceso a los recursos de WebJars
                .antMatchers("/editar/**", "/agregar/**", "/eliminar/**").hasRole("ADMIN")
                .antMatchers("/").hasAnyRole("USER", "ADMIN")
                .antMatchers("/anonymous*").anonymous()
                .and()
            .formLogin()
                .loginPage("/login")
                .and()
            .exceptionHandling()
                .accessDeniedPage("/error/403");
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
