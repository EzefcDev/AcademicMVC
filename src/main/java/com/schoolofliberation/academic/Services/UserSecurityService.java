package com.schoolofliberation.academic.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.schoolofliberation.academic.Repositories.UserRepository;
import com.schoolofliberation.academic.entities.UserEntity;
import com.schoolofliberation.academic.entities.UserRoleEntity;

@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserSecurityService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = this.userRepository.findOneByEmail(username);

        String[] roles = userEntity.getRoles().stream().map(UserRoleEntity::getRole).toArray(String[]::new);

        return User.builder()
                .username(userEntity.getName())
                .password(userEntity.getPassword())
                .authorities(this.grantedAuthorities(roles))
                .accountLocked(userEntity.getLocked())
                .disabled(userEntity.getDisabled())
                .build();
    }

    //Esto es para usar permisos especificos de acceso

    private String[] getAuthorities(String role){
        if ("ADMIN".equals(role) || "USER".equals(role)) {
           return new String[] { "random_order"}; 
        }

        return new String[] {};
    }
    
    private List<GrantedAuthority> grantedAuthorities(String[] roles){
        List<GrantedAuthority> authorities = new ArrayList<>(roles.length);

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

            for (String authority : this.getAuthorities(role)) {
                authorities.add(new SimpleGrantedAuthority(authority));
            }
        }

        return authorities;
    }
}
