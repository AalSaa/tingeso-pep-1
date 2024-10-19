package com.example.PrestaBancoBackend.services;

import com.example.PrestaBancoBackend.entities.LoanTypeEntity;
import com.example.PrestaBancoBackend.repositories.LoanTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanTypeService {
    @Autowired
    private LoanTypeRepository loanTypeRepository;

    public List<LoanTypeEntity> getAllLoanTypes() {
        return loanTypeRepository.findAll();
    }

    public LoanTypeEntity getLoanTypeById(Long id) {
        Optional<LoanTypeEntity> loanType = loanTypeRepository.findById(id);

        if (loanType.isEmpty()) {
            throw new EntityNotFoundException("Loan type not found");
        }

        return loanType.get();
    }
}
