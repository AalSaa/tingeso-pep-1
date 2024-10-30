package com.example.PrestaBancoBackend.services;

import com.example.PrestaBancoBackend.dtos.LoanDTO;
import com.example.PrestaBancoBackend.dtos.LoanUpdateDTO;
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

    public LoanEntity createLoan(LoanDTO loanDTO) {
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

        BigDecimal monthlyCost = getMonthlyCost(loanDTO);

        LoanEntity loan = LoanEntity.builder()
                .propertyValue(loanDTO.getPropertyValue())
                .amount(loanDTO.getAmount())
                .termInYears(loanDTO.getTermInYears())
                .annualInterestRate(loanDTO.getAnnualInterestRate())
                .monthlyLifeInsurance(loanDTO.getMonthlyLifeInsurance())
                .monthlyFireInsurance(loanDTO.getMonthlyFireInsurance())
                .administrationFee(loanDTO.getAdministrationFee())
                .status(loanDTO.getStatus())
                .monthlyCost(monthlyCost)
                .loanType(loanType)
                .user(user)
                .build();

        return loanRepository.save(loan);
    }

    public LoanEntity updateLoan(Long id, LoanUpdateDTO loanDTO) {
        Optional<LoanEntity> possibleLoan = loanRepository.findById(id);

        if (possibleLoan.isEmpty()) {
            throw new EntityNotFoundException("Loan not found");
        }

        LoanEntity loan = possibleLoan.get();

        verifyLoanTypeRestrictions(loanDTO, loan.getLoanType());

        BigDecimal monthlyCost = getMonthlyCost(loanDTO);

        LoanEntity updatedLoan = LoanEntity.builder()
                .id(loan.getId())
                .propertyValue(loanDTO.getPropertyValue())
                .amount(loanDTO.getAmount())
                .termInYears(loanDTO.getTermInYears())
                .annualInterestRate(loanDTO.getAnnualInterestRate())
                .monthlyLifeInsurance(loanDTO.getMonthlyLifeInsurance())
                .monthlyFireInsurance(loanDTO.getMonthlyFireInsurance())
                .administrationFee(loanDTO.getAdministrationFee())
                .status(loanDTO.getStatus())
                .monthlyCost(monthlyCost)
                .loanType(loan.getLoanType())
                .user(loan.getUser())
                .build();

        return loanRepository.save(updatedLoan);
    }

    public void deleteLoanById(Long id) {
        if (!loanRepository.existsById(id)) {
            throw new EntityNotFoundException("Loan not found");
        }
        loanRepository.deleteById(id);
    }

    public BigDecimal getMonthlyCost(LoanDTO loanDTO) {
        int termInMonths = loanDTO.getTermInYears() * 12;
        BigDecimal monthlyInterestRate = BigDecimal.valueOf(loanDTO.getAnnualInterestRate())
                .divide(BigDecimal.valueOf(12))
                .divide(BigDecimal.valueOf(100));

        BigDecimal numerator = loanDTO.getAmount()
                .multiply(monthlyInterestRate)
                .multiply(monthlyInterestRate.add(BigDecimal.ONE).pow(termInMonths));
        BigDecimal denominator = monthlyInterestRate.add(BigDecimal.ONE).pow(termInMonths)
                .subtract(BigDecimal.ONE);

        return numerator.divide(denominator, 0, RoundingMode.HALF_UP);
    }

    public BigDecimal getMonthlyCost(LoanUpdateDTO loanDTO) {
        int termInMonths = loanDTO.getTermInYears() * 12;
        BigDecimal monthlyInterestRate = BigDecimal.valueOf(loanDTO.getAnnualInterestRate())
                .divide(BigDecimal.valueOf(12))
                .divide(BigDecimal.valueOf(100));

        BigDecimal numerator = loanDTO.getAmount()
                .multiply(monthlyInterestRate)
                .multiply(monthlyInterestRate.add(BigDecimal.ONE).pow(termInMonths));
        BigDecimal denominator = monthlyInterestRate.add(BigDecimal.ONE).pow(termInMonths)
                .subtract(BigDecimal.ONE);

        return numerator.divide(denominator, 0, RoundingMode.HALF_UP);
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

    public void verifyLoanTypeRestrictions(LoanDTO loanDTO, LoanTypeEntity loanType) {
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

    public void verifyLoanTypeRestrictions(LoanUpdateDTO loanDTO, LoanTypeEntity loanType) {
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
