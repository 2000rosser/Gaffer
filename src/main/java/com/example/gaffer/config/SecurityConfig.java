package com.example.gaffer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http
    //         .authorizeHttpRequests((requests) -> requests
    //             .requestMatchers("/", "/home", "/login", "/register", "/h2-console/**").permitAll()  // Ensure H2 console is accessible
    //             .requestMatchers(HttpMethod.POST, "/api/user").anonymous()
    //             .requestMatchers(HttpMethod.POST, "/api/user/verify").anonymous()
    //             .anyRequest().authenticated()
    //         )
    //         .formLogin((form) -> form
    //             .loginPage("/login")
    //             .permitAll()
    //         )
    //         .logout((logout) -> logout.permitAll())
    //         .csrf(csrf -> csrf
    //             .ignoringRequestMatchers("/h2-console/**")  // Disable CSRF protection for H2 console
    //         )
    //         .headers(headers -> headers
    //             .frameOptions(frameOptions -> frameOptions.sameOrigin())  // Allow H2 console frames
    //         );

    //     return http.build();
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();
        
        return new InMemoryUserDetailsManager(user);
    }
}



