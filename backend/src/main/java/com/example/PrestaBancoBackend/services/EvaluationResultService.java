package com.example.PrestaBancoBackend.services;

import com.example.PrestaBancoBackend.entities.*;
import com.example.PrestaBancoBackend.repositories.EvaluationInfoRepository;
import com.example.PrestaBancoBackend.repositories.EvaluationResultRepository;
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
public class EvaluationResultService {
    @Autowired
    private EvaluationResultRepository evaluationResultRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private EvaluationInfoRepository evaluationInfoRepository;

    public List<EvaluationResultEntity> getAllEvaluations() {
        return evaluationResultRepository.findAll();
    }

    public EvaluationResultEntity getEvaluationById(Long id) {
        Optional<EvaluationResultEntity> evaluation = evaluationResultRepository.findById(id);

        if (evaluation.isEmpty()) {
            throw new EntityNotFoundException("Evaluation not found");
        }

        return evaluation.get();
    }

    public EvaluationResultEntity generateEvaluationResult(Long loanId) {
        Optional<LoanEntity> possibleLoan = loanRepository.findById(loanId);

        if (possibleLoan.isEmpty()) {
            throw new EntityNotFoundException("Loan not found");
        }

        LoanEntity loan = possibleLoan.get();

        Optional<EvaluationInfoEntity> possibleEvaluationInfo =
                evaluationInfoRepository.findById(loan.getEvaluationInfo().getId());

        if (possibleEvaluationInfo.isEmpty()) {
            throw new EntityNotFoundException("Evaluation info not found");
        }

        EvaluationInfoEntity evaluationInfo = possibleEvaluationInfo.get();

        EvaluationResultEntity evaluation = analyzeEvaluationData(evaluationInfo, loan);

        evaluation.setEvaluationResult(getEvaluationResult(evaluation));
        evaluation.setEvaluationInfo(evaluationInfo);

        return evaluationResultRepository.save(evaluation);
    }

    public EvaluationResultEntity analyzeEvaluationData(EvaluationInfoEntity evaluationInfo, LoanEntity loan) {

        return EvaluationResultEntity.builder()
                .isIncomeExpenseRatioValid(
                        analyzeIncomeExpenseRatio(loan.getMonthlyCost(), evaluationInfo.getMonthlyIncome())
                )
                .isCreditHistoryValid(
                        analyzeCreditHistory(evaluationInfo.getHavePositiveCreditHistory())
                )
                .isEmploymentStabilityValid(
                        analyzeEmploymentStability(
                                evaluationInfo.getEmploymentType(),
                                evaluationInfo.getEmploymentSeniority()
                        )
                )
                .isDebtIncomeRatioValid(
                        analyzeDebtIncomeRatio(
                                evaluationInfo.getMonthlyDebt(),
                                loan.getMonthlyCost(),
                                evaluationInfo.getMonthlyIncome()
                        )
                )
                .isAgeAtLoanEndValid(
                        analyzeAgeAtLoanEnd(loan.getUser().getBirthDate())
                )
                .isMinimumBalanceRequiredValid(
                        analyzeMinimumBalanceRequired(evaluationInfo.getSavingsAccountBalance(), loan.getAmount())
                )
                .isConsistentSavingsHistoryValid(
                        analyzeConsistentSavingsHistory(evaluationInfo.getHavePositiveCreditHistory())
                )
                .isPeriodicDepositsValid(
                        analyzePeriodicDeposits(
                                evaluationInfo.getHasPeriodicDeposits(),
                                evaluationInfo.getSumOfDeposits(),
                                evaluationInfo.getMonthlyIncome()
                        )
                )
                .isBalanceYearsRatioValid(
                        analyzeBalanceYearsRatio(
                                evaluationInfo.getOldSavingsAccount(),
                                evaluationInfo.getSavingsAccountBalance(),
                                loan.getAmount()
                        )
                )
                .isRecentWithdrawalsValid(
                        analyzeRecentWithdrawals(
                                evaluationInfo.getMaximumWithdrawalInSixMonths(),
                                evaluationInfo.getSavingsAccountBalance())
                )
                .build();
    }

    public String getEvaluationResult(EvaluationResultEntity entity) {
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
