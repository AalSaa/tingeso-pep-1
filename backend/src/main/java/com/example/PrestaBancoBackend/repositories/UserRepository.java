package com.example.PrestaBancoBackend.repositories;

import com.example.PrestaBancoBackend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByRut(String rut);
}
