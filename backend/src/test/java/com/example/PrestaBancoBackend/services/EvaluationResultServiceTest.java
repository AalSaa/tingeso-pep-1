package com.example.PrestaBancoBackend.services;

import com.example.PrestaBancoBackend.entities.EvaluationInfoEntity;
import com.example.PrestaBancoBackend.entities.EvaluationResultEntity;
import com.example.PrestaBancoBackend.entities.LoanEntity;
import com.example.PrestaBancoBackend.entities.UserEntity;
import com.example.PrestaBancoBackend.repositories.EvaluationInfoRepository;
import com.example.PrestaBancoBackend.repositories.EvaluationResultRepository;
import com.example.PrestaBancoBackend.repositories.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class EvaluationResultServiceTest {
    @Mock
    EvaluationResultRepository evaluationResultRepository;
    @Mock
    LoanRepository loanRepository;
    @Mock
    EvaluationInfoRepository evaluationInfoRepository;

    @InjectMocks
    EvaluationResultService evaluationResultService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetAllEvaluations_HaveOneEvaluationResult() {
        // Given
        EvaluationResultEntity evaluationResult1 = new EvaluationResultEntity();

        // When
        when(evaluationResultRepository.findAll()).thenReturn(Arrays.asList(evaluationResult1));
        List<EvaluationResultEntity> evaluationResults = evaluationResultService.getAllEvaluations();

        // Then
        assertThat(evaluationResults).hasSize(1);
        assertThat(evaluationResults).contains(evaluationResult1);
    }

    @Test
    void whenGetAllEvaluations_HaveTwoEvaluationResults() {
        // Given
        EvaluationResultEntity evaluationResult1 = new EvaluationResultEntity();
        EvaluationResultEntity evaluationResult2 = new EvaluationResultEntity();

        // When
        when(evaluationResultRepository.findAll()).thenReturn(Arrays.asList(evaluationResult1, evaluationResult2));
        List<EvaluationResultEntity> evaluationResults = evaluationResultService.getAllEvaluations();

        // Then
        assertThat(evaluationResults).hasSize(2);
        assertThat(evaluationResults).contains(evaluationResult1, evaluationResult2);
    }

    @Test
    void whenGetAllEvaluations_HaveThreeEvaluationResults() {
        // Given
        EvaluationResultEntity evaluationResult1 = new EvaluationResultEntity();
        EvaluationResultEntity evaluationResult2 = new EvaluationResultEntity();
        EvaluationResultEntity evaluationResult3 = new EvaluationResultEntity();

        // When
        when(evaluationResultRepository.findAll())
                .thenReturn(Arrays.asList(evaluationResult1, evaluationResult2, evaluationResult3));
        List<EvaluationResultEntity> evaluationResults = evaluationResultService.getAllEvaluations();

        // Then
        assertThat(evaluationResults).hasSize(3);
        assertThat(evaluationResults).contains(evaluationResult1, evaluationResult2, evaluationResult3);
    }

    @Test
    void whenGetAllEvaluations_HaveFourEvaluationResults() {
        // Given
        EvaluationResultEntity evaluationResult1 = new EvaluationResultEntity();
        EvaluationResultEntity evaluationResult2 = new EvaluationResultEntity();
        EvaluationResultEntity evaluationResult3 = new EvaluationResultEntity();
        EvaluationResultEntity evaluationResult4 = new EvaluationResultEntity();

        // When
        when(evaluationResultRepository.findAll())
                .thenReturn(Arrays.asList(evaluationResult1, evaluationResult2, evaluationResult3, evaluationResult4));
        List<EvaluationResultEntity> evaluationResults = evaluationResultService.getAllEvaluations();

        // Then
        assertThat(evaluationResults).hasSize(4);
        assertThat(evaluationResults)
                .contains(evaluationResult1, evaluationResult2, evaluationResult3, evaluationResult4);
    }

    @Test
    void whenGetAllEvaluations_DontHaveEvaluationResults() {
        // When
        when(evaluationResultRepository.findAll())
                .thenReturn(new ArrayList<>());
        List<EvaluationResultEntity> evaluationResults = evaluationResultService.getAllEvaluations();

        // Then
        assertThat(evaluationResults).isEqualTo(new ArrayList<EvaluationResultEntity>());
    }

    @Test
    void whenGetEvaluationById_EvaluationResultFound() {
        Long evaluationResultId = 1L;
        EvaluationResultEntity evaluationResult = EvaluationResultEntity.builder().id(evaluationResultId).build();

        // When
        when(evaluationResultRepository.findById(evaluationResultId)).thenReturn(Optional.of(evaluationResult));
        EvaluationResultEntity foundEvaluationResult = evaluationResultService.getEvaluationById(evaluationResultId);

        // Then
        assertThat(foundEvaluationResult).isNotNull();
        assertThat(foundEvaluationResult).isEqualTo(evaluationResult);
    }

    @Test
    void whenGetEvaluationById_MultipleEvaluationResultsExists() {
        // Given
        Long evaluationResultId = 1L;
        EvaluationResultEntity evaluationResult = EvaluationResultEntity.builder().id(evaluationResultId).build();
        EvaluationResultEntity evaluationResult2 = EvaluationResultEntity.builder().id(2L).build();

        // When
        when(evaluationResultRepository.findById(evaluationResultId)).thenReturn(Optional.of(evaluationResult));
        EvaluationResultEntity foundEvaluationResult = evaluationResultService.getEvaluationById(evaluationResultId);

        // Then
        assertThat(foundEvaluationResult).isNotNull();
        assertThat(foundEvaluationResult).isEqualTo(evaluationResult);
        assertThat(foundEvaluationResult).isNotEqualTo(evaluationResult2);
    }

    @Test
    void whenGetEvaluationById_EvaluationResultNotFound() {
        // Given
        Long evaluationResultId = 1L;

        // When
        when(evaluationResultRepository.findById(evaluationResultId)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> evaluationResultService.getEvaluationById(evaluationResultId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Evaluation not found");
    }

    @Test
    void whenGetEvaluationById_IdIsNull() {
        // When
        when(evaluationResultRepository.findById(null)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> evaluationResultService.getEvaluationById(null))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Evaluation not found");
    }

    @Test
    void whenGetEvaluationById_IdIsNegative(){
        // Given
        Long invalidId = -1L;

        // When
        when(evaluationResultRepository.findById(invalidId)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> evaluationResultService.getEvaluationById(invalidId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Evaluation not found");
    }

    @Test
    void whenGenerateEvaluationResult_GenerateEvaluationResultAndApproved() {
        // Given
        UserEntity user = UserEntity.builder()
                .id(1L)
                .birthDate(Date.valueOf("1990-01-01"))
                .build();

        EvaluationInfoEntity evaluationInfo = EvaluationInfoEntity.builder()
                .id(2L)
                .monthlyIncome(BigDecimal.valueOf(4000000))
                .havePositiveCreditHistory(true)
                .employmentType("Dependent")
                .employmentSeniority(4)
                .monthlyDebt(BigDecimal.valueOf(300000))
                .savingsAccountBalance(BigDecimal.valueOf(15000000))
                .hasConsistentSavingsHistory(true)
                .hasPeriodicDeposits(true)
                .sumOfDeposits(BigDecimal.valueOf(2500000))
                .oldSavingsAccount(3)
                .maximumWithdrawalInSixMonths(BigDecimal.ZERO)
                .build();

        Long loanId = 3L;
        LoanEntity loan = LoanEntity.builder()
                .id(loanId)
                .propertyValue(BigDecimal.valueOf(200000000))
                .amount(BigDecimal.valueOf(100000000))
                .termInYears(20)
                .annualInterestRatePercentage(4.5)
                .user(user)
                .evaluationInfo(evaluationInfo)
                .monthlyFee(BigDecimal.valueOf(632649))
                .build();

        EvaluationResultEntity expectedEvaluationResult = EvaluationResultEntity.builder()
                .isIncomeExpenseRatioValid(true)
                .isCreditHistoryValid(true)
                .isEmploymentStabilityValid(true)
                .isDebtIncomeRatioValid(true)
                .isAgeAtLoanEndValid(true)
                .isMinimumBalanceRequiredValid(true)
                .isConsistentSavingsHistoryValid(true)
                .isPeriodicDepositsValid(true)
                .isBalanceYearsRatioValid(true)
                .isRecentWithdrawalsValid(true)
                .evaluationResult("Approved")
                .evaluationInfo(evaluationInfo)
                .build();

        // When
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
        when(evaluationInfoRepository.findById(loan.getEvaluationInfo().getId()))
                .thenReturn(Optional.of(evaluationInfo));
        when(evaluationResultRepository.save(any(EvaluationResultEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        EvaluationResultEntity generatedEvaluationResult = evaluationResultService.generateEvaluationResult(loanId);

        assertThat(generatedEvaluationResult).isNotNull();
        assertThat(generatedEvaluationResult).isEqualTo(expectedEvaluationResult);
    }

    @Test
    void whenGenerateEvaluationResult_GenerateEvaluationResultAndRejected() {
        // Given
        UserEntity user = UserEntity.builder()
                .id(1L)
                .birthDate(Date.valueOf("1990-01-01"))
                .build();

        EvaluationInfoEntity evaluationInfo = EvaluationInfoEntity.builder()
                .id(2L)
                .monthlyIncome(BigDecimal.valueOf(4000000))
                .havePositiveCreditHistory(false)
                .employmentType("Dependent")
                .employmentSeniority(4)
                .monthlyDebt(BigDecimal.valueOf(300000))
                .savingsAccountBalance(BigDecimal.valueOf(15000000))
                .hasConsistentSavingsHistory(true)
                .hasPeriodicDeposits(true)
                .sumOfDeposits(BigDecimal.valueOf(2500000))
                .oldSavingsAccount(3)
                .maximumWithdrawalInSixMonths(BigDecimal.ZERO)
                .build();

        Long loanId = 3L;
        LoanEntity loan = LoanEntity.builder()
                .id(loanId)
                .propertyValue(BigDecimal.valueOf(200000000))
                .amount(BigDecimal.valueOf(100000000))
                .termInYears(20)
                .annualInterestRatePercentage(4.5)
                .user(user)
                .evaluationInfo(evaluationInfo)
                .monthlyFee(BigDecimal.valueOf(632649))
                .build();

        EvaluationResultEntity expectedEvaluationResult = EvaluationResultEntity.builder()
                .isIncomeExpenseRatioValid(true)
                .isCreditHistoryValid(false)
                .isEmploymentStabilityValid(true)
                .isDebtIncomeRatioValid(true)
                .isAgeAtLoanEndValid(true)
                .isMinimumBalanceRequiredValid(true)
                .isConsistentSavingsHistoryValid(true)
                .isPeriodicDepositsValid(true)
                .isBalanceYearsRatioValid(true)
                .isRecentWithdrawalsValid(true)
                .evaluationResult("Rejected")
                .evaluationInfo(evaluationInfo)
                .build();

        // When
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
        when(evaluationInfoRepository.findById(loan.getEvaluationInfo().getId()))
                .thenReturn(Optional.of(evaluationInfo));
        when(evaluationResultRepository.save(any(EvaluationResultEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        EvaluationResultEntity generatedEvaluationResult = evaluationResultService.generateEvaluationResult(loanId);

        assertThat(generatedEvaluationResult).isNotNull();
        assertThat(generatedEvaluationResult).isEqualTo(expectedEvaluationResult);
    }

    @Test
    void whenGenerateEvaluationResult_GenerateEvaluationResultButRequiresAAdditionalReview() {
        // Given
        UserEntity user = UserEntity.builder()
                .id(1L)
                .birthDate(Date.valueOf("1990-01-01"))
                .build();

        EvaluationInfoEntity evaluationInfo = EvaluationInfoEntity.builder()
                .id(2L)
                .monthlyIncome(BigDecimal.valueOf(4000000))
                .havePositiveCreditHistory(true)
                .employmentType("Dependent")
                .employmentSeniority(4)
                .monthlyDebt(BigDecimal.valueOf(300000))
                .savingsAccountBalance(BigDecimal.valueOf(15000000))
                .hasConsistentSavingsHistory(false)
                .hasPeriodicDeposits(true)
                .sumOfDeposits(BigDecimal.valueOf(2500000))
                .oldSavingsAccount(3)
                .maximumWithdrawalInSixMonths(BigDecimal.ZERO)
                .build();

        Long loanId = 3L;
        LoanEntity loan = LoanEntity.builder()
                .id(loanId)
                .propertyValue(BigDecimal.valueOf(200000000))
                .amount(BigDecimal.valueOf(100000000))
                .termInYears(20)
                .annualInterestRatePercentage(4.5)
                .user(user)
                .evaluationInfo(evaluationInfo)
                .monthlyFee(BigDecimal.valueOf(632649))
                .build();

        EvaluationResultEntity expectedEvaluationResult = EvaluationResultEntity.builder()
                .isIncomeExpenseRatioValid(true)
                .isCreditHistoryValid(true)
                .isEmploymentStabilityValid(true)
                .isDebtIncomeRatioValid(true)
                .isAgeAtLoanEndValid(true)
                .isMinimumBalanceRequiredValid(true)
                .isConsistentSavingsHistoryValid(false)
                .isPeriodicDepositsValid(true)
                .isBalanceYearsRatioValid(true)
                .isRecentWithdrawalsValid(true)
                .evaluationResult("Requires a additional review")
                .evaluationInfo(evaluationInfo)
                .build();

        // When
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
        when(evaluationInfoRepository.findById(loan.getEvaluationInfo().getId()))
                .thenReturn(Optional.of(evaluationInfo));
        when(evaluationResultRepository.save(any(EvaluationResultEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        EvaluationResultEntity generatedEvaluationResult = evaluationResultService.generateEvaluationResult(loanId);

        assertThat(generatedEvaluationResult).isNotNull();
        assertThat(generatedEvaluationResult).isEqualTo(expectedEvaluationResult);
    }

    @Test
    void whenGenerateEvaluationResult_LoanNotFound() {
        // Given
        Long loanId = 1L;

        // When
        when(loanRepository.findById(loanId)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> evaluationResultService.generateEvaluationResult(loanId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Loan not found");
    }

    @Test
    void whenGenerateEvaluationResult_EvaluationInfoNotFound() {
        // Given
        Long loanId = 1L;
        LoanEntity loan = new LoanEntity();
        loan.setEvaluationInfo(new EvaluationInfoEntity());

        // When
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
        when(evaluationInfoRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> evaluationResultService.generateEvaluationResult(loanId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Evaluation info not found");
    }
}
