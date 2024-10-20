package com.example.PrestaBancoBackend.repositories;

import com.example.PrestaBancoBackend.entities.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
}
