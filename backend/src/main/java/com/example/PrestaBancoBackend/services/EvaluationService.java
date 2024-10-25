package com.example.PrestaBancoBackend.services;

import com.example.PrestaBancoBackend.dtos.EvaluationDTO;
import com.example.PrestaBancoBackend.entities.*;
import com.example.PrestaBancoBackend.repositories.EvaluationInfoRepository;
import com.example.PrestaBancoBackend.repositories.EvaluationRepository;
import com.example.PrestaBancoBackend.repositories.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class EvaluationService {
    @Autowired
    private EvaluationRepository evaluationRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private EvaluationInfoRepository evaluationInfoRepository;

    public List<EvaluationEntity> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    public EvaluationEntity getEvaluationById(Long id) {
        Optional<EvaluationEntity> evaluation = evaluationRepository.findById(id);

        if (evaluation.isEmpty()) {
            throw new EntityNotFoundException("Evaluation not found");
        }

        return evaluation.get();
    }

    public EvaluationEntity createEvaluation(EvaluationDTO evaluationDTO) {
        Optional<LoanEntity> possibleLoan = loanRepository.findById(evaluationDTO.getLoanId());

        if (possibleLoan.isEmpty()) {
            throw new EntityNotFoundException("Loan not found");
        }

        LoanEntity loan = possibleLoan.get();

        EvaluationEntity evaluation = analyzeEvaluationData(evaluationDTO, loan);

        evaluation.setEvaluationResult(getEvaluationResult(evaluation));
        evaluation.setLoan(loan);

        EvaluationEntity savedEvaluation = evaluationRepository.save(evaluation);

        EvaluationInfoEntity evaluationInfo = EvaluationInfoEntity.builder()
                .monthlyIncome(evaluationDTO.getMonthlyIncome())
                .havePositiveCreditHistory(evaluationDTO.getHavePositiveCreditHistory())
                .employmentType(evaluationDTO.getEmploymentType())
                .employmentSeniority(evaluationDTO.getEmploymentSeniority())
                .monthlyDebt(evaluationDTO.getMonthlyDebt())
                .savingsAccountBalance(evaluationDTO.getSavingsAccountBalance())
                .hasPeriodicDeposits(evaluationDTO.getHasPeriodicDeposits())
                .sumOfDeposits(evaluationDTO.getSumOfDeposits())
                .oldSavingsAccount(evaluationDTO.getOldSavingsAccount())
                .maximumWithdrawalInSixMonths(evaluationDTO.getMaximumWithdrawalInSixMonths())
                .evaluation(savedEvaluation)
                .build();

        evaluationInfoRepository.save(evaluationInfo);

        return savedEvaluation;
    }

    public EvaluationEntity analyzeEvaluationData(EvaluationDTO evaluationDTO, LoanEntity loan) {

        return EvaluationEntity.builder()
                .isIncomeExpenseRatioValid(
                        analyzeIncomeExpenseRatio(loan.getMonthlyCost(), evaluationDTO.getMonthlyIncome())
                )
                .isCreditHistoryValid(
                        analyzeCreditHistory(evaluationDTO.getHavePositiveCreditHistory())
                )
                .isEmploymentStabilityValid(
                        analyzeEmploymentStability(
                                evaluationDTO.getEmploymentType(),
                                evaluationDTO.getEmploymentSeniority()
                        )
                )
                .isDebtIncomeRatioValid(
                        analyzeDebtIncomeRatio(
                                evaluationDTO.getMonthlyDebt(),
                                loan.getMonthlyCost(),
                                evaluationDTO.getMonthlyIncome()
                        )
                )
                .isAgeAtLoanEndValid(
                        analyzeAgeAtLoanEnd(loan.getUser().getBirthDate())
                )
                .isMinimumBalanceRequiredValid(
                        analyzeMinimumBalanceRequired(evaluationDTO.getSavingsAccountBalance(), loan.getAmount())
                )
                .isConsistentSavingsHistoryValid(
                        analyzeConsistentSavingsHistory(evaluationDTO.getHavePositiveCreditHistory())
                )
                .isPeriodicDepositsValid(
                        analyzePeriodicDeposits(
                                evaluationDTO.getHasPeriodicDeposits(),
                                evaluationDTO.getSumOfDeposits(),
                                evaluationDTO.getMonthlyIncome()
                        )
                )
                .isBalanceYearsRatioValid(
                        analyzeBalanceYearsRatio(
                                evaluationDTO.getOldSavingsAccount(),
                                evaluationDTO.getSavingsAccountBalance(),
                                loan.getAmount()
                        )
                )
                .isRecentWithdrawalsValid(
                        analyzeRecentWithdrawals(
                                evaluationDTO.getMaximumWithdrawalInSixMonths(),
                                evaluationDTO.getSavingsAccountBalance())
                )
                .build();
    }

    public String getEvaluationResult(EvaluationEntity entity) {
        int nValidatedSavingsCapacityRules = 0;

        if (entity.getIsMinimumBalanceRequiredValid()) {
            nValidatedSavingsCapacityRules++;
        }
        if (entity.getIsConsistentSavingsHistoryValid()) {
            nValidatedSavingsCapacityRules++;
        }
        if (entity.getIsPeriodicDepositsValid()) {
            nValidatedSavingsCapacityRules++;
        }
        if (entity.getIsBalanceYearsRatioValid()) {
            nValidatedSavingsCapacityRules++;
        }
        if (entity.getIsRecentWithdrawalsValid()) {
            nValidatedSavingsCapacityRules++;
        }

        if (!entity.getIsIncomeExpenseRatioValid()
                || !entity.getIsCreditHistoryValid()
                || !entity.getIsEmploymentStabilityValid()
                || !entity.getIsDebtIncomeRatioValid()
                || !entity.getIsAgeAtLoanEndValid()
                || nValidatedSavingsCapacityRules <= 2
        ) {
            return "Rejected";
        } else if (nValidatedSavingsCapacityRules < 5) {
            return "Requires a additional review";
        } else {
            return "Approved";
        }
    }

    public Boolean analyzeIncomeExpenseRatio(BigDecimal monthlyCost, BigDecimal monthlyIncome) {
        BigDecimal incomeExpenseRatio = monthlyCost
                .multiply(BigDecimal.valueOf(100))
                .divide(monthlyIncome);

        return incomeExpenseRatio.compareTo(BigDecimal.valueOf(35)) <= 0;
    }

    public Boolean analyzeCreditHistory(Boolean havePositiveCreditHistory) {
        return havePositiveCreditHistory;
    }

    public Boolean analyzeEmploymentStability(String employmentType, Integer employmentSeniority) {
        return (employmentType.equals("Dependent") && employmentSeniority >= 1)
                || (employmentType.equals("Independent") && employmentSeniority >= 2);
    }

    public Boolean analyzeDebtIncomeRatio(BigDecimal monthlyDebt, BigDecimal monthlyCost, BigDecimal monthlyIncome) {
        return monthlyDebt.add(monthlyCost)
                .compareTo(monthlyIncome.multiply(BigDecimal.valueOf(0.5))) <= 0;
    }

    public Boolean analyzeAgeAtLoanEnd(Date birthDate) {
        return Period.between(birthDate.toLocalDate(), LocalDate.now()).getYears() < 70;
    }

    public Boolean analyzeMinimumBalanceRequired(BigDecimal savingsAccountBalance, BigDecimal amount) {
        return savingsAccountBalance
                .compareTo(amount.multiply(BigDecimal.valueOf(0.1))) >= 0;
    }

    public Boolean analyzeConsistentSavingsHistory(Boolean HavePositiveCreditHistory) {
        return HavePositiveCreditHistory;
    }

    public Boolean analyzePeriodicDeposits(Boolean hasPeriodicDeposits, BigDecimal sumOfDeposits, BigDecimal monthlyIncome) {
        return hasPeriodicDeposits
                && (sumOfDeposits
                .compareTo(monthlyIncome.multiply(BigDecimal.valueOf(0.5))) >= 0);
    }

    public Boolean analyzeBalanceYearsRatio(Integer oldSavingsAccount, BigDecimal savingsAccountBalance, BigDecimal amount) {
        return  (oldSavingsAccount < 2
                && savingsAccountBalance
                .compareTo(amount.multiply(BigDecimal.valueOf(0.2))) >= 0)
                || (oldSavingsAccount >= 2
                && savingsAccountBalance
                .compareTo(amount.multiply(BigDecimal.valueOf(0.1))) >= 0
        );
    }

    public Boolean analyzeRecentWithdrawals(BigDecimal maximumWithdrawalInSixMonths, BigDecimal savingsAccountBalance) {
        return maximumWithdrawalInSixMonths
                .compareTo(savingsAccountBalance.multiply(BigDecimal.valueOf(0.3))) < 0;
    }
}
