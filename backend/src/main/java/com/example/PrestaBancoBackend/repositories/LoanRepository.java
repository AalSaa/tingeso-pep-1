package com.example.PrestaBancoBackend.repositories;

import com.example.PrestaBancoBackend.entities.LoanEntity;
import com.example.PrestaBancoBackend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    List<LoanEntity> findAllByUser(UserEntity user);

    List<LoanEntity> findAllByStatus(String status);

    List<LoanEntity> findAllByStatusNot(String status);

    List<LoanEntity> findAllByUserIdAndStatusNot(Long userId, String status);
}
