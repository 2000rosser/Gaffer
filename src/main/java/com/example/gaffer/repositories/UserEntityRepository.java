package com.example.gaffer.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gaffer.models.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByVerificationCode(String code);

    List<UserEntity> findByAutoEnabledTrue();
}
