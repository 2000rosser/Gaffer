package com.example.gaffer.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.gaffer.models.ReferenceRequestDTO;
import com.example.gaffer.models.UserEntity;
import com.example.gaffer.repositories.UserEntityRepository;

@Service
public class UserService {

    UserEntityRepository repository;

    public UserService(UserEntityRepository repository){
        this.repository=repository;
    }

    public ReferenceRequestDTO getUserProfile(Long userId) {
        UserEntity user = repository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new ReferenceRequestDTO(user.getName(), user.getUsername(), user.getLocation(), user.getDescription(), 
                                    user.getReferences(), user.getOccupation(), user.getPlaceOfWork());
    }
}
