package com.example.PrestaBancoBackend.repositories;

import com.example.PrestaBancoBackend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByRut(String rut);

    Boolean existsByRutAndIdNot(String rut, Long id);
}
