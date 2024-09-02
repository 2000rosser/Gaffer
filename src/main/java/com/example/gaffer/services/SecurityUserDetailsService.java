package com.example.gaffer.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.gaffer.repositories.UserEntityRepository;


@Service
public class SecurityUserDetailsService implements UserDetailsService {
    private final UserEntityRepository repository;

    public SecurityUserDetailsService(UserEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return repository
        .findByUsername(email)
        .map(entity -> User
            .builder()
            .username(entity.getUsername())
            .password(entity.getPassword())
            .authorities(entity.getAuthorities())
            .accountExpired(!entity.isAccountNonExpired())
            .accountLocked(!entity.isAccountNonLocked())
            .credentialsExpired(!entity.isCredentialsNonExpired())
            .disabled(!entity.isEnabled())
            .build())
        .orElseThrow(() -> new UsernameNotFoundException("No user found for the given e-mail address"));
}
}

