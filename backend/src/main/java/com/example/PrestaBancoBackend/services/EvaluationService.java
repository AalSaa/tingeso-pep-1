package com.example.PrestaBancoBackend.services;

import com.example.PrestaBancoBackend.dtos.EvaluationCreateDTO;
import com.example.PrestaBancoBackend.entities.EvaluationEntity;
import com.example.PrestaBancoBackend.entities.LoanEntity;
import com.example.PrestaBancoBackend.repositories.EvaluationRepository;
import com.example.PrestaBancoBackend.repositories.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationService {
    @Autowired
    private EvaluationRepository evaluationRepository;
    @Autowired
    private LoanRepository loanRepository;

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

    public EvaluationEntity createEvaluation(EvaluationCreateDTO evaluationDTO) {
        Optional<LoanEntity> possibleLoan = loanRepository.findById(evaluationDTO.getLoanId());

        if (possibleLoan.isEmpty()) {
            throw new EntityNotFoundException("Loan not found");
        }

        LoanEntity loan = possibleLoan.get();

        String result = getEvaluationResult(evaluationDTO);

        EvaluationEntity evaluation = EvaluationEntity.builder()
                .isIncomeExpenseRatioValid(evaluationDTO.getIsIncomeExpenseRatioValid())
                .isCreditHistoryValid(evaluationDTO.getIsCreditHistoryValid())
                .isEmploymentStabilityValid(evaluationDTO.getIsEmploymentStabilityValid())
                .isDebtIncomeRatioValid(evaluationDTO.getIsDebtIncomeRatioValid())
                .isAgeAtLoanEndValid(evaluationDTO.getIsAgeAtLoanEndValid())
                .isMinimumBalanceRequiredValid(evaluationDTO.getIsMinimumBalanceRequiredValid())
                .isConsistentSavingsHistoryValid(evaluationDTO.getIsConsistentSavingsHistoryValid())
                .isPeriodicDepositsValid(evaluationDTO.getIsPeriodicDepositsValid())
                .isBalanceYearsRatioValid(evaluationDTO.getIsBalanceYearsRatioValid())
                .isRecentWithdrawalsValid(evaluationDTO.getIsRecentWithdrawalsValid())
                .evaluationResult(result)
                .loan(loan)
                .build();

        return evaluationRepository.save(evaluation);
    }

    public String getEvaluationResult(EvaluationCreateDTO evaluationDTO) {
        int nValidatedSavingsCapacityRules = 0;

        if (evaluationDTO.getIsMinimumBalanceRequiredValid()) {
            nValidatedSavingsCapacityRules++;
        }
        if (evaluationDTO.getIsConsistentSavingsHistoryValid()) {
            nValidatedSavingsCapacityRules++;
        }
        if (evaluationDTO.getIsPeriodicDepositsValid()) {
            nValidatedSavingsCapacityRules++;
        }
        if (evaluationDTO.getIsBalanceYearsRatioValid()) {
            nValidatedSavingsCapacityRules++;
        }
        if (evaluationDTO.getIsRecentWithdrawalsValid()) {
            nValidatedSavingsCapacityRules++;
        }

        if (!evaluationDTO.getIsIncomeExpenseRatioValid()
                || !evaluationDTO.getIsCreditHistoryValid()
                || !evaluationDTO.getIsEmploymentStabilityValid()
                || !evaluationDTO.getIsDebtIncomeRatioValid()
                || !evaluationDTO.getIsAgeAtLoanEndValid()
                || nValidatedSavingsCapacityRules <= 2
        ) {
            return "Rejected";
        } else if (nValidatedSavingsCapacityRules < 5) {
            return "Requires a additional review";
        } else {
            return "Approved";
        }
    }
}
