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
                admin.setDescription("Hi, I am interested in renting your property. Please let me know what you require and I'll provide it.");
                admin.setPhoneNumber("0872970140");
                admin.setName("Ross Murphy");
                admin.setOccupation("Software Engineer");
                admin.setProfilePicture("/images/default_user_profile_picture.png");
                admin.setPassword(passwordEncoder.encode("a"));
                admin.setEnabled(true);
                admin.setAccountNonExpired(true);
                admin.setAccountNonLocked(true);
                admin.setCredentialsNonExpired(true);
                admin.setRoles(new ArrayList<>(List.of("ROLE_ADMIN")));
                admin.setVerificationCode(null);

                repository.save(admin);

            }
            if (repository.findByUsername("rossmurphy974@gmail.com").isEmpty()) {
                UserEntity admin = new UserEntity();
                admin.setUsername("rossmurphy974@gmail.com");
                admin.setDescription("Hi, I am interested in renting your property. Please let me know what you require and I'll provide it.");
                admin.setPhoneNumber("0872970140");
                admin.setProfilePicture("/images/default_user_profile_picture.png");
                admin.setName("Johnnie");
                admin.setPassword(passwordEncoder.encode("a"));
                admin.setEnabled(true);
                admin.setAccountNonExpired(true);
                admin.setAccountNonLocked(true);
                admin.setCredentialsNonExpired(true);
                admin.setRoles(new ArrayList<>(List.of("ROLE_ADMIN")));
                admin.setVerificationCode(null);

                repository.save(admin);

            }

            if (repository.findByUsername("kamilko199@gmail.com").isEmpty()) {
                UserEntity admin = new UserEntity();
                admin.setUsername("kamilko199@gmail.com");
                admin.setDescription("Hi, I am interested in renting your property. Please let me know what you require and I'll provide it.");
                admin.setPhoneNumber("0872970140");
                admin.setProfilePicture("/images/default_user_profile_picture.png");
                admin.setName("Kamil Murphy");
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

