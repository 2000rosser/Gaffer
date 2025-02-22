package com.example.gaffer.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.gaffer.models.Reference;
import com.example.gaffer.models.UserDto;
import com.example.gaffer.models.UserEntity;
import com.example.gaffer.repositories.UserEntityRepository;

@Service
public class UserService {

    UserEntityRepository repository;

    public UserService(UserEntityRepository repository){
        this.repository=repository;
    }

    public UserDto getUserProfile(Long userId) {
        UserEntity user = repository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserDto(user.getId(), user.getName(), user.getPhoneNumber(), user.getLocation(),
        user.getDescription(), user.getOccupation(), user.getUsername(), user.getPlaceOfWork(),
        user.getProfilePicture(), user.getIdDoc(), user.getWorkDoc(), user.getLandDoc());
    }

    public UserDto getUserProfileWithEntity(UserEntity user) {
        return new UserDto(user.getId(), user.getName(), user.getPhoneNumber(), user.getLocation(),
        user.getDescription(), user.getOccupation(), user.getUsername(), user.getPlaceOfWork(),
        user.getProfilePicture(), user.getIdDoc(), user.getWorkDoc(), user.getLandDoc());
    }

    public void updateUserProfile(UserEntity entity, UserEntity updatedUser) {
        entity.setName(updatedUser.getName());
        entity.setUsername(updatedUser.getUsername());
        entity.setPhoneNumber(updatedUser.getPhoneNumber());
        entity.setLocation(updatedUser.getLocation());
        entity.setDescription(updatedUser.getDescription());
        entity.setOccupation(updatedUser.getOccupation());
        entity.setPlaceOfWork(updatedUser.getPlaceOfWork());

        repository.save(entity);
    }

    public Optional<UserEntity> findUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    public void addReferenceToUser(UserEntity userEntity, Reference reference) {
        userEntity.getReferences().add(reference);
        repository.save(userEntity);
    }
}
