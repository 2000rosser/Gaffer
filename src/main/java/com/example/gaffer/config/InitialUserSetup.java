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
                admin.setProfilePicture("https://media.daft.ie/eyJidWNrZXQiOiJtZWRpYW1hc3Rlci1zM2V1IiwiZWRpdHMiOnsib3ZlcmxheVdpdGgiOnsiYnVja2V0IjoibWVkaWFtYXN0ZXItczNldSIsIm9wdGlvbnMiOnsiZ3Jhdml0eSI6ImNlbnRyZSJ9LCJrZXkiOiJ3YXRlcm1hcmstZGFmdC1sb2dvLWxhcmdlLXYzLnBuZyJ9LCJyZXNpemUiOnsiZml0IjoiY292ZXIiLCJ3aWR0aCI6NzIwLCJoZWlnaHQiOjQ4MH19LCJvdXRwdXRGb3JtYXQiOiJqcGVnIiwia2V5IjoiYy8zL2MzZmUzMjVkNzU2YzRlYTZiNThiMzE3YzgyMjQwZDBhLmpwZyJ9?signature=369df980dc188caa6a6e368b9a4c4c7f9db45ec8e1c675562e01dbee7476084c");
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
                admin.setProfilePicture("https://media.daft.ie/eyJidWNrZXQiOiJtZWRpYW1hc3Rlci1zM2V1IiwiZWRpdHMiOnsib3ZlcmxheVdpdGgiOnsiYnVja2V0IjoibWVkaWFtYXN0ZXItczNldSIsIm9wdGlvbnMiOnsiZ3Jhdml0eSI6ImNlbnRyZSJ9LCJrZXkiOiJ3YXRlcm1hcmstZGFmdC1sb2dvLWxhcmdlLXYzLnBuZyJ9LCJyZXNpemUiOnsiZml0IjoiY292ZXIiLCJ3aWR0aCI6NzIwLCJoZWlnaHQiOjQ4MH19LCJvdXRwdXRGb3JtYXQiOiJqcGVnIiwia2V5IjoiYy8zL2MzZmUzMjVkNzU2YzRlYTZiNThiMzE3YzgyMjQwZDBhLmpwZyJ9?signature=369df980dc188caa6a6e368b9a4c4c7f9db45ec8e1c675562e01dbee7476084c");
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
                admin.setProfilePicture("https://media.daft.ie/eyJidWNrZXQiOiJtZWRpYW1hc3Rlci1zM2V1IiwiZWRpdHMiOnsib3ZlcmxheVdpdGgiOnsiYnVja2V0IjoibWVkaWFtYXN0ZXItczNldSIsIm9wdGlvbnMiOnsiZ3Jhdml0eSI6ImNlbnRyZSJ9LCJrZXkiOiJ3YXRlcm1hcmstZGFmdC1sb2dvLWxhcmdlLXYzLnBuZyJ9LCJyZXNpemUiOnsiZml0IjoiY292ZXIiLCJ3aWR0aCI6NzIwLCJoZWlnaHQiOjQ4MH19LCJvdXRwdXRGb3JtYXQiOiJqcGVnIiwia2V5IjoiYy8zL2MzZmUzMjVkNzU2YzRlYTZiNThiMzE3YzgyMjQwZDBhLmpwZyJ9?signature=369df980dc188caa6a6e368b9a4c4c7f9db45ec8e1c675562e01dbee7476084c");
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

