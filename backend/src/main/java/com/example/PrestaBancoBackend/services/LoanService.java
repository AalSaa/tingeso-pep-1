package com.example.PrestaBancoBackend.services;

import com.example.PrestaBancoBackend.dtos.LoanCreateDTO;
import com.example.PrestaBancoBackend.dtos.LoanResponseDTO;
import com.example.PrestaBancoBackend.entities.LoanEntity;
import com.example.PrestaBancoBackend.entities.LoanTypeEntity;
import com.example.PrestaBancoBackend.entities.UserEntity;
import com.example.PrestaBancoBackend.repositories.LoanRepository;
import com.example.PrestaBancoBackend.repositories.LoanTypeRepository;
import com.example.PrestaBancoBackend.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoanTypeRepository loanTypeRepository;

    public List<LoanEntity> getAllLoans() {
        return loanRepository.findAll();
    }

    public LoanEntity getLoanById(Long id) {
        Optional<LoanEntity> loan = loanRepository.findById(id);

        if (loan.isEmpty()) {
            throw new EntityNotFoundException("Loan not found");
        }

        return loan.get();
    }

    public LoanResponseDTO createLoan(LoanCreateDTO loanDTO) {
        Optional<UserEntity> possibleUser = userRepository.findById(loanDTO.getUserId());
        if (possibleUser.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        UserEntity user = possibleUser.get();

        verifyUserRestrictions(user);

        Optional<LoanTypeEntity> possibleLoanType = loanTypeRepository.findById(loanDTO.getLoanTypeId());
        if (possibleLoanType.isEmpty()) {
            throw new EntityNotFoundException("Loan type not found");
        }
        LoanTypeEntity loanType = possibleLoanType.get();

        verifyLoanTypeRestrictions(loanDTO, loanType);

        LoanEntity loan = LoanEntity.builder()
                .propertyValue(loanDTO.getPropertyValue())
                .amount(loanDTO.getAmount())
                .termInYears(loanDTO.getTermInYears())
                .annualInterestRate(loanDTO.getAnnualInterestRate())
                .monthlyLifeInsurance(loanDTO.getMonthlyLifeInsurance())
                .monthlyFireInsurance(loanDTO.getMonthlyFireInsurance())
                .administrationFee(loanDTO.getAdministrationFee())
                .status(loanDTO.getStatus())
                .loanType(loanType)
                .user(user)
                .build();

        LoanEntity savedLoan = loanRepository.save(loan);
        return LoanResponseDTO.builder()
                .loan(savedLoan)
                .monthlyCost(getMonthlyCost(savedLoan))
                .build();
    }

    public void deleteLoanById(Long id) {
        if (!loanRepository.existsById(id)) {
            throw new EntityNotFoundException("Loan not found");
        }
        loanRepository.deleteById(id);
    }

    public BigDecimal getMonthlyCost(LoanEntity loan) {
        int termInMonths = loan.getTermInYears() * 12;
        BigDecimal monthlyInterestRate = BigDecimal.valueOf(loan.getAnnualInterestRate())
                .divide(BigDecimal.valueOf(12))
                .divide(BigDecimal.valueOf(100));

        BigDecimal numerator = loan.getAmount()
                .multiply(monthlyInterestRate)
                .multiply(monthlyInterestRate.add(BigDecimal.ONE).pow(termInMonths));
        BigDecimal denominator = monthlyInterestRate.add(BigDecimal.ONE).pow(termInMonths)
                .subtract(BigDecimal.ONE);

        BigDecimal monthlyCost = numerator.divide(denominator, 0, RoundingMode.HALF_UP);
        return monthlyCost;
    }

    public void verifyUserRestrictions(UserEntity user) {
        if(!user.getStatus().equals("Active")) {
            throw new IllegalStateException("User is not active");
        }

        List<LoanEntity> loansOfUser = loanRepository.findAllByUser(user);

        for (LoanEntity loan : loansOfUser) {
            if (!loan.getStatus().equals("Closed")) {
                throw new IllegalStateException("The user already has a loan in process");
            }
        }
    }

    public void verifyLoanTypeRestrictions(LoanCreateDTO loanDTO, LoanTypeEntity loanType) {
        BigDecimal maxPercentage = BigDecimal.valueOf(loanType.getMaxPercentageAmount())
                .divide(BigDecimal.valueOf(100));

        BigDecimal maxAllowedAmount = loanDTO.getPropertyValue().multiply(maxPercentage);

        if (loanDTO.getAmount().compareTo(maxAllowedAmount) > 0) {
            throw new IllegalStateException("Amount exceeds max allowed amount");
        }

        if (loanDTO.getTermInYears() > loanType.getMaxTerm()) {
            throw new IllegalStateException("The term limit exceeded");
        }
    }
}
