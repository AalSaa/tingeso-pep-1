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
import jakarta.validation.Valid;
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

    public List<LoanEntity> getAllLoansByStatus(String status) {
        return loanRepository.findAllByStatus(status);
    }

    public List<LoanEntity> getAllLoansByStatusNot(String status) {
        return loanRepository.findAllByStatusNot(status);
    }

    public List<LoanEntity> getAllLoansByUserIdAndStatusNot(Long userId, String status) {
        return loanRepository.findAllByUserIdAndStatusNot(userId, status);
    }

    public LoanEntity getLoanById(Long id) {
        Optional<LoanEntity> loan = loanRepository.findById(id);

        if (loan.isEmpty()) {
            throw new EntityNotFoundException("Loan not found");
        }

        return loan.get();
    }

    public LoanEntity createLoan(@Valid LoanDTO loanDTO) {
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

        verifyLoanTypeRestrictions(
                loanDTO.getPropertyValue(),
                loanDTO.getAmount(),
                loanDTO.getTermInYears(),
                loanType
        );

        BigDecimal monthlyFee = getMonthlyFee(
                loanDTO.getTermInYears(),
                loanDTO.getAnnualInterestRatePercentage(),
                loanDTO.getAmount()
        );

        BigDecimal monthlyCost = getMonthlyCost(
                loanDTO.getAmount(),
                monthlyFee,
                loanDTO.getMonthlyLifeInsurancePercentage(),
                loanDTO.getMonthlyFireInsuranceAmountPercentage()
        );

        BigDecimal totalCost = getTotalCost(
                monthlyCost,
                loanDTO.getTermInYears(),
                loanDTO.getAmount(),
                loanDTO.getAdministrationFeePercentage()
        );

        LoanEntity loan = LoanEntity.builder()
                .propertyValue(loanDTO.getPropertyValue())
                .amount(loanDTO.getAmount())
                .termInYears(loanDTO.getTermInYears())
                .annualInterestRatePercentage(loanDTO.getAnnualInterestRatePercentage())
                .monthlyLifeInsurancePercentage(loanDTO.getMonthlyLifeInsurancePercentage())
                .monthlyFireInsuranceAmountPercentage(loanDTO.getMonthlyFireInsuranceAmountPercentage())
                .administrationFeePercentage(loanDTO.getAdministrationFeePercentage())
                .status(loanDTO.getStatus())
                .monthlyFee(monthlyFee)
                .monthlyCost(monthlyCost)
                .totalCost(totalCost)
                .loanType(loanType)
                .user(user)
                .build();

        return loanRepository.save(loan);
    }

    public LoanEntity updateLoan(@Valid Long id, LoanUpdateDTO loanDTO) {
        Optional<LoanEntity> possibleLoan = loanRepository.findById(id);

        if (possibleLoan.isEmpty()) {
            throw new EntityNotFoundException("Loan not found");
        }

        LoanEntity loan = possibleLoan.get();

        verifyLoanTypeRestrictions(
                loanDTO.getPropertyValue(),
                loanDTO.getAmount(),
                loanDTO.getTermInYears(),
                loan.getLoanType()
        );

        BigDecimal monthlyFee = getMonthlyFee(
                loanDTO.getTermInYears(),
                loanDTO.getAnnualInterestRatePercentage(),
                loanDTO.getAmount()
        );

        BigDecimal monthlyCost = getMonthlyCost(
                loanDTO.getAmount(),
                monthlyFee,
                loanDTO.getMonthlyLifeInsurancePercentage(),
                loanDTO.getMonthlyFireInsuranceAmountPercentage()
        );

        BigDecimal totalCost = getTotalCost(
                monthlyCost,
                loanDTO.getTermInYears(),
                loanDTO.getAmount(),
                loanDTO.getAdministrationFeePercentage()
        );

        LoanEntity updatedLoan = LoanEntity.builder()
                .id(loan.getId())
                .propertyValue(loanDTO.getPropertyValue())
                .amount(loanDTO.getAmount())
                .termInYears(loanDTO.getTermInYears())
                .annualInterestRatePercentage(loanDTO.getAnnualInterestRatePercentage())
                .monthlyLifeInsurancePercentage(loanDTO.getMonthlyLifeInsurancePercentage())
                .monthlyFireInsuranceAmountPercentage(loanDTO.getMonthlyFireInsuranceAmountPercentage())
                .administrationFeePercentage(loanDTO.getAdministrationFeePercentage())
                .monthlyFee(monthlyFee)
                .monthlyCost(monthlyCost)
                .totalCost(totalCost)
                .status(loanDTO.getStatus())
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

    public BigDecimal getMonthlyFee(Integer termInYears, Double annualInterestRatePercentage, BigDecimal amount) {
        int termInMonths = termInYears * 12;
        BigDecimal monthlyInterestRate = BigDecimal.valueOf(annualInterestRatePercentage)
                .divide(BigDecimal.valueOf(12))
                .divide(BigDecimal.valueOf(100));

        BigDecimal numerator = amount
                .multiply(monthlyInterestRate)
                .multiply(monthlyInterestRate.add(BigDecimal.ONE).pow(termInMonths));
        BigDecimal denominator = monthlyInterestRate.add(BigDecimal.ONE).pow(termInMonths)
                .subtract(BigDecimal.ONE);

        return numerator.divide(denominator, 0, RoundingMode.HALF_UP);
    }

    public BigDecimal getMonthlyCost(
            BigDecimal amount,
            BigDecimal monthlyFee,
            Double monthlyLifeInsurancePercentage,
            Double monthlyFireInsurancePercentage
    ) {
        BigDecimal monthlyLifeInsuranceAmount =
                amount.multiply(BigDecimal.valueOf(monthlyLifeInsurancePercentage).divide(BigDecimal.valueOf(100)));
        BigDecimal monthlyFireInsuranceAmount =
                amount.multiply((BigDecimal.valueOf(monthlyFireInsurancePercentage).divide(BigDecimal.valueOf(100))));

        return monthlyFee.add(monthlyLifeInsuranceAmount).add(monthlyFireInsuranceAmount);
    }

    public BigDecimal getTotalCost(
            BigDecimal monthlyCost,
            Integer termInYears,
            BigDecimal amount,
            Double administrationFeePercentage
    ) {
        BigDecimal administrationFeeAmount =
                amount.multiply(BigDecimal.valueOf(administrationFeePercentage).divide(BigDecimal.valueOf(100)));

        return monthlyCost.multiply(BigDecimal.valueOf(termInYears).multiply(BigDecimal.valueOf(12)))
                .add(administrationFeeAmount);
    }

    public void verifyUserRestrictions(UserEntity user) {
        if(!user.getStatus().equals("Active")) {
            throw new IllegalStateException("User is not active");
        }

        List<LoanEntity> loansOfUser = loanRepository.findAllByUser(user);

        for (LoanEntity loan : loansOfUser) {
            if (!(
                    loan.getStatus().equals("Aprobada") ||
                    loan.getStatus().equals("Cancelada") ||
                    loan.getStatus().equals("Requiere revisión adicional") ||
                    loan.getStatus().equals("Simulación")
            )) {
                throw new IllegalStateException("The user already has a loan in process");
            }
        }
    }

    public void verifyLoanTypeRestrictions(
            BigDecimal propertyValue,
            BigDecimal amount,
            Integer termInYears,
            LoanTypeEntity loanType
    ) {
        BigDecimal maxPercentage = BigDecimal.valueOf(loanType.getMaxPercentageAmount())
                .divide(BigDecimal.valueOf(100));

        BigDecimal maxAllowedAmount = propertyValue.multiply(maxPercentage);

        if (amount.compareTo(maxAllowedAmount) > 0) {
            throw new IllegalStateException("Amount exceeds max allowed amount");
        }

        if (termInYears > loanType.getMaxTerm()) {
            throw new IllegalStateException("The term limit exceeded");
        }
    }
}
