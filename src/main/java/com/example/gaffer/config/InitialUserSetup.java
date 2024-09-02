package com.example.gaffer.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.gaffer.models.UserEntity;
import com.example.gaffer.repositories.UserEntityRepository;

@Configuration
public class InitialUserSetup {

    private final UserEntityRepository repository;
    private final PasswordEncoder passwordEncoder;

    public InitialUserSetup(UserEntityRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner createDefaultUser() {
        return args -> {
            if (repository.findByUsername("a@a.com").isEmpty()) {
                UserEntity admin = new UserEntity();
                admin.setUsername("a@a.com");
                admin.setPassword(passwordEncoder.encode("a"));
                admin.setEnabled(true);
                admin.setAccountNonExpired(true);
                admin.setAccountNonLocked(true);
                admin.setCredentialsNonExpired(true);
                admin.setRoles(new ArrayList<>(List.of("ROLE_ADMIN")));
                admin.setVerificationCode(null);

                repository.save(admin);

            }
        };
    }
}

