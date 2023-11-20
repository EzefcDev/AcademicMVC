package com.schoolofliberation.academic.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolofliberation.academic.entities.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
    UserEntity findOneByEmail(String email);
}
