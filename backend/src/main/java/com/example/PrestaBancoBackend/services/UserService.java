package com.example.PrestaBancoBackend.services;

import com.example.PrestaBancoBackend.entities.UserEntity;
import com.example.PrestaBancoBackend.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);

        if(user.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }

        return user.get();
    }

    public UserEntity createUser(UserEntity user) {
        verifyUserData(user);

        if(userRepository.existsByRut(user.getRut())) {
            throw new IllegalStateException("The rut is already used");
        }

        return userRepository.save(user);
    }

    public UserEntity updateUser(UserEntity user) {
        Optional<UserEntity> userOptional = userRepository.findById(user.getId());
        if(userOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }

        verifyUserData(user);

        if(userRepository.existsByRutAndIdNot(user.getRut(), user.getId())) {
            throw new IllegalStateException("The rut is already used");
        }

        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        if(!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    public void verifyUserData(UserEntity user) {
        if(user.getRut() == null || user.getRut().isEmpty()) {
            throw new IllegalArgumentException("Rut is required");
        }
        if(user.getFirstName() == null || user.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }
        if(user.getLastName() == null || user.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if(user.getBirthDate() == null) {
            throw new IllegalArgumentException("Birth date is required");
        }
        if(user.getStatus() == null || user.getStatus().isEmpty()) {
            throw new IllegalArgumentException("Status is required");
        }
    }
}