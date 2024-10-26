package com.example.PrestaBancoBackend.repositories;

import com.example.PrestaBancoBackend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByRut(String rut);

    Boolean existsByRut(String rut);

    Boolean existsByRutAndIdNot(String rut, Long id);
}
