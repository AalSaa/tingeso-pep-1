package com.example.PrestaBancoBackend.services;

import com.example.PrestaBancoBackend.dtos.EvaluationInfoDTO;
import com.example.PrestaBancoBackend.dtos.EvaluationInfoUpdateDTO;
import com.example.PrestaBancoBackend.entities.EvaluationInfoEntity;
import com.example.PrestaBancoBackend.entities.LoanEntity;
import com.example.PrestaBancoBackend.repositories.EvaluationInfoRepository;
import com.example.PrestaBancoBackend.repositories.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationInfoService {
    @Autowired
    private EvaluationInfoRepository evaluationInfoRepository;
    @Autowired
    private LoanRepository loanRepository;

    public List<EvaluationInfoEntity> getAllEvaluationInfo(){
        return evaluationInfoRepository.findAll();
    }

    public EvaluationInfoEntity getEvaluationInfoById(Long id){
        Optional<EvaluationInfoEntity> evaluationInfo = evaluationInfoRepository.findById(id);

        if (evaluationInfo.isEmpty()) {
            throw new EntityNotFoundException("Evaluation info not found");
        }

        return evaluationInfo.get();
    }

    public EvaluationInfoEntity getEvaluationInfoByLoanId(Long loanId){
        Optional<EvaluationInfoEntity> evaluationInfo = evaluationInfoRepository.findByLoanId(loanId);

        if (evaluationInfo.isEmpty()) {
            throw new EntityNotFoundException("Evaluation info not found");
        }

        return evaluationInfo.get();
    }

    public EvaluationInfoEntity saveEvaluationInfo(@Valid EvaluationInfoDTO evaluationInfoDTO){
        Optional<LoanEntity> possibleLoan = loanRepository.findById(evaluationInfoDTO.getLoanId());

        if(possibleLoan.isEmpty()){
            throw new EntityNotFoundException("Loan not found");
        }

        LoanEntity loan = possibleLoan.get();

        EvaluationInfoEntity evaluationInfoEntity = EvaluationInfoEntity.builder()
                .monthlyIncome(evaluationInfoDTO.getMonthlyIncome())
                .havePositiveCreditHistory(evaluationInfoDTO.getHavePositiveCreditHistory())
                .employmentType(evaluationInfoDTO.getEmploymentType())
                .employmentSeniority(evaluationInfoDTO.getEmploymentSeniority())
                .monthlyDebt(evaluationInfoDTO.getMonthlyDebt())
                .savingsAccountBalance(evaluationInfoDTO.getSavingsAccountBalance())
                .hasConsistentSavingsHistory(evaluationInfoDTO.getHasConsistentSavingsHistory())
                .hasPeriodicDeposits(evaluationInfoDTO.getHasPeriodicDeposits())
                .sumOfDeposits(evaluationInfoDTO.getSumOfDeposits())
                .oldSavingsAccount(evaluationInfoDTO.getOldSavingsAccount())
                .maximumWithdrawalInSixMonths(evaluationInfoDTO.getMaximumWithdrawalInSixMonths())
                .loan(loan)
                .build();

        return evaluationInfoRepository.save(evaluationInfoEntity);
    }

    public EvaluationInfoEntity updateEvaluationInfo(Long id, @Valid EvaluationInfoUpdateDTO evaluationInfoDTO) {
        Optional<EvaluationInfoEntity> possibleEvaluationInfo = evaluationInfoRepository.findById(id);
        if (possibleEvaluationInfo.isEmpty()) {
            throw new EntityNotFoundException("Evaluation info not found");
        }

        EvaluationInfoEntity evaluationInfoEntity = EvaluationInfoEntity.builder()
                .id(id)
                .monthlyIncome(evaluationInfoDTO.getMonthlyIncome())
                .havePositiveCreditHistory(evaluationInfoDTO.getHavePositiveCreditHistory())
                .employmentType(evaluationInfoDTO.getEmploymentType())
                .employmentSeniority(evaluationInfoDTO.getEmploymentSeniority())
                .monthlyDebt(evaluationInfoDTO.getMonthlyDebt())
                .savingsAccountBalance(evaluationInfoDTO.getSavingsAccountBalance())
                .hasConsistentSavingsHistory(evaluationInfoDTO.getHasConsistentSavingsHistory())
                .hasPeriodicDeposits(evaluationInfoDTO.getHasPeriodicDeposits())
                .sumOfDeposits(evaluationInfoDTO.getSumOfDeposits())
                .oldSavingsAccount(evaluationInfoDTO.getOldSavingsAccount())
                .maximumWithdrawalInSixMonths(evaluationInfoDTO.getMaximumWithdrawalInSixMonths())
                .loan(possibleEvaluationInfo.get().getLoan())
                .build();

        return evaluationInfoRepository.save(evaluationInfoEntity);
    }
}
