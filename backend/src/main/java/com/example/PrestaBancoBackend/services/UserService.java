package com.example.PrestaBancoBackend.services;

import com.example.PrestaBancoBackend.dtos.UserDTO;
import com.example.PrestaBancoBackend.entities.UserEntity;
import com.example.PrestaBancoBackend.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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

    public UserEntity getUserByRut(String rut) {
        Optional<UserEntity> user = userRepository.findByRut(rut);

        if(user.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }

        return user.get();
    }

    public UserEntity createUser(@Valid UserDTO userDTO) {
        if(userRepository.existsByRut(userDTO.getRut())) {
            throw new IllegalStateException("The rut is already used");
        }

        UserEntity user = UserEntity.builder()
                .rut(userDTO.getRut())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .birthDate(userDTO.getBirthDate())
                .status(userDTO.getStatus())
                .build();
        return userRepository.save(user);
    }

    public UserEntity updateUser(@Valid Long id, UserDTO userDTO) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }

        if(userRepository.existsByRutAndIdNot(userDTO.getRut(), id)) {
            throw new IllegalStateException("The rut is already used");
        }

        UserEntity updatedUser = UserEntity.builder()
                .id(id)
                .rut(userDTO.getRut())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .birthDate(userDTO.getBirthDate())
                .status(userDTO.getStatus())
                .build();

        return userRepository.save(updatedUser);
    }

    public void deleteUserById(Long id) {
        if(!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }
}