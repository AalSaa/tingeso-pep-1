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

    // ###################################################
    @Test
    void whenGetEvaluationResult_Rejected() {
        // Given
        EvaluationResultEntity evaluationResult = EvaluationResultEntity.builder()
                .isIncomeExpenseRatioValid(false) // Esta condición es inválida
                .isCreditHistoryValid(true)
                .isEmploymentStabilityValid(true)
                .isDebtIncomeRatioValid(true)
                .isAgeAtLoanEndValid(true)
                .isMinimumBalanceRequiredValid(true)
                .isConsistentSavingsHistoryValid(true)
                .isPeriodicDepositsValid(true)
                .isBalanceYearsRatioValid(true)
                .isRecentWithdrawalsValid(true)
                .build();

        // When
        String result = evaluationResultService.getEvaluationResult(evaluationResult);

        // Then
        assertThat(result).isEqualTo("Rejected");
    }

    @Test
    void whenGetEvaluationResult_Approved() {
        // Given
        EvaluationResultEntity evaluationResult = EvaluationResultEntity.builder()
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
                .build();

        // When
        String result = evaluationResultService.getEvaluationResult(evaluationResult);

        // Then
        assertThat(result).isEqualTo("Approved");
    }

    @Test
    void whenGetEvaluationResult_RequiresAdditionalReview() {
        // Given
        EvaluationResultEntity evaluationResult = EvaluationResultEntity.builder()
                .isIncomeExpenseRatioValid(true)
                .isCreditHistoryValid(true)
                .isEmploymentStabilityValid(true)
                .isDebtIncomeRatioValid(true)
                .isAgeAtLoanEndValid(true)
                .isMinimumBalanceRequiredValid(true)
                .isConsistentSavingsHistoryValid(false)
                .isPeriodicDepositsValid(false)
                .isBalanceYearsRatioValid(true)
                .isRecentWithdrawalsValid(true)
                .build();

        // When
        String result = evaluationResultService.getEvaluationResult(evaluationResult);

        // Then
        assertThat(result).isEqualTo("Requires a additional review");
    }

    @Test
    void whenGetEvaluationResult_Rejected_InsufficientSavingsCapacity() {
        // Given
        EvaluationResultEntity evaluationResult = EvaluationResultEntity.builder()
                .isIncomeExpenseRatioValid(true)
                .isCreditHistoryValid(true)
                .isEmploymentStabilityValid(true)
                .isDebtIncomeRatioValid(true)
                .isAgeAtLoanEndValid(true)
                .isMinimumBalanceRequiredValid(false)
                .isConsistentSavingsHistoryValid(false)
                .isPeriodicDepositsValid(false)
                .isBalanceYearsRatioValid(false)
                .isRecentWithdrawalsValid(false)
                .build();

        // When
        String result = evaluationResultService.getEvaluationResult(evaluationResult);

        // Then
        assertThat(result).isEqualTo("Rejected");
    }

    @Test
    void whenGetEvaluationResult_RequiresAdditionalReview_AllRejected() {
        // Given
        EvaluationResultEntity evaluationResult = EvaluationResultEntity.builder()
                .isIncomeExpenseRatioValid(false)
                .isCreditHistoryValid(false)
                .isEmploymentStabilityValid(false)
                .isDebtIncomeRatioValid(false)
                .isAgeAtLoanEndValid(false)
                .isMinimumBalanceRequiredValid(false)
                .isConsistentSavingsHistoryValid(false)
                .isPeriodicDepositsValid(false)
                .isBalanceYearsRatioValid(false)
                .isRecentWithdrawalsValid(false)
                .build();

        // When
        String result = evaluationResultService.getEvaluationResult(evaluationResult);

        // Then
        assertThat(result).isEqualTo("Rejected");
    }

    @Test
    void whenAnalyzeIncomeExpenseRatio_ValidRatio() {
        // Given
        BigDecimal monthlyFee = BigDecimal.valueOf(600000);
        BigDecimal monthlyIncome = BigDecimal.valueOf(4000000);

        // When
        Boolean result = evaluationResultService.analyzeIncomeExpenseRatio(monthlyFee, monthlyIncome);

        // Then
        assertThat(result).isEqualTo(true);
    }

    @Test
    void whenAnalyzeIncomeExpenseRatio_InvalidRatio() {
        // Given
        BigDecimal monthlyFee = BigDecimal.valueOf(600000);
        BigDecimal monthlyIncome = BigDecimal.valueOf(1000000);

        // When
        Boolean result = evaluationResultService.analyzeIncomeExpenseRatio(monthlyFee, monthlyIncome);

        // Then
        assertThat(result).isEqualTo(false);
    }

    @Test
    void whenAnalyzeEmploymentStability_DependentWithSufficientSeniority() {
        // Given
        String employmentType = "Dependent";
        Integer employmentSeniority = 2;

        // When
        Boolean result = evaluationResultService.analyzeEmploymentStability(employmentType, employmentSeniority);

        // Then
        assertThat(result).isEqualTo(true);
    }

    @Test
    void whenAnalyzeEmploymentStability_DependentButNotWithSufficientSeniority() {
        // Given
        String employmentType = "Dependent";
        Integer employmentSeniority = 0;

        // When
        Boolean result = evaluationResultService.analyzeEmploymentStability(employmentType, employmentSeniority);

        // Then
        assertThat(result).isEqualTo(false);
    }

    @Test
    void whenAnalyzeEmploymentStability_IndependentWithSufficientSeniority() {
        // Given
        String employmentType = "Independent";
        Integer employmentSeniority = 2;

        // When
        Boolean result = evaluationResultService.analyzeEmploymentStability(employmentType, employmentSeniority);

        // Then
        assertThat(result).isEqualTo(true);
    }

    @Test
    void whenAnalyzeEmploymentStability_IndependentButNotWithSufficientSeniority() {
        // Given
        String employmentType = "Independent";
        Integer employmentSeniority = 0;

        // When
        Boolean result = evaluationResultService.analyzeEmploymentStability(employmentType, employmentSeniority);

        // Then
        assertThat(result).isEqualTo(false);
    }

    @Test
    void whenAnalyzeDebtIncomeRatio_ValidScenario() {
        // Given
        BigDecimal monthlyDebt = BigDecimal.valueOf(300000);
        BigDecimal monthlyFee = BigDecimal.valueOf(600000);
        BigDecimal monthlyIncome = BigDecimal.valueOf(4000000);

        // When
        Boolean result = evaluationResultService.analyzeDebtIncomeRatio(monthlyDebt, monthlyFee, monthlyIncome);

        // Then
        assertThat(result).isEqualTo(true);
    }

    @Test
    void whenAnalyzeDebtIncomeRatio_InvalidScenario() {
        // Given
        BigDecimal monthlyDebt = BigDecimal.valueOf(300000);
        BigDecimal monthlyFee = BigDecimal.valueOf(600000);
        BigDecimal monthlyIncome = BigDecimal.valueOf(1000000);

        // When
        Boolean result = evaluationResultService.analyzeDebtIncomeRatio(monthlyDebt, monthlyFee, monthlyIncome);

        // Then
        assertThat(result).isEqualTo(false);
    }

    @Test
    void whenAnalyzeAgeAtLoanEnd_ValidAge() {
        // Given
        Date birthDate = Date.valueOf("1990-01-01");

        // When
        Boolean result = evaluationResultService.analyzeAgeAtLoanEnd(birthDate);

        // Then
        assertThat(result).isEqualTo(true);
    }

    @Test
    void whenAnalyzeAgeAtLoanEnd_InvalidAge() {
        // Given
        Date birthDate = Date.valueOf("1945-01-01");

        // When
        Boolean result = evaluationResultService.analyzeAgeAtLoanEnd(birthDate);

        // Then
        assertThat(result).isEqualTo(false);
    }

    @Test
    void whenAnalyzeMinimumBalanceRequired_SufficientBalance() {
        // Given
        BigDecimal savingsAccountBalance = BigDecimal.valueOf(20000000);
        BigDecimal amount = BigDecimal.valueOf(100000000);

        // When
        Boolean result = evaluationResultService.analyzeMinimumBalanceRequired(savingsAccountBalance, amount);

        // Then
        assertThat(result).isEqualTo(true);
    }

    @Test
    void whenAnalyzeMinimumBalanceRequired_InsufficientBalance() {
        // Given
        BigDecimal savingsAccountBalance = BigDecimal.valueOf(5000000);
        BigDecimal amount = BigDecimal.valueOf(100000000);

        // When
        Boolean result = evaluationResultService.analyzeMinimumBalanceRequired(savingsAccountBalance, amount);

        // Then
        assertThat(result).isEqualTo(false);
    }

    @Test
    void whenAnalyzePeriodicDeposits_SufficientDeposits() {
        // Given
        Boolean hasPeriodicDeposits = true;
        BigDecimal sumOfDeposits = BigDecimal.valueOf(1500000);
        BigDecimal monthlyIncome = BigDecimal.valueOf(2000000);

        // When
        Boolean result =
                evaluationResultService.analyzePeriodicDeposits(hasPeriodicDeposits, sumOfDeposits, monthlyIncome);

        // Then
        assertThat(result).isEqualTo(true);
    }

    @Test
    void whenAnalyzePeriodicDeposits_DontHavePeriodicDeposits() {
        // Given
        Boolean hasPeriodicDeposits = false;
        BigDecimal sumOfDeposits = BigDecimal.valueOf(1500000);
        BigDecimal monthlyIncome = BigDecimal.valueOf(2000000);

        // When
        Boolean result =
                evaluationResultService.analyzePeriodicDeposits(hasPeriodicDeposits, sumOfDeposits, monthlyIncome);

        // Then
        assertThat(result).isEqualTo(false);
    }

    @Test
    void whenAnalyzePeriodicDeposits_InsufficientDeposits() {
        // Given
        Boolean hasPeriodicDeposits = true;
        BigDecimal sumOfDeposits = BigDecimal.valueOf(500000);
        BigDecimal monthlyIncome = BigDecimal.valueOf(2000000);

        // When
        Boolean result =
                evaluationResultService.analyzePeriodicDeposits(hasPeriodicDeposits, sumOfDeposits, monthlyIncome);

        // Then
        assertThat(result).isEqualTo(false);
    }

    @Test
    void whenAnalyzeBalanceYearsRatio_NewAccount_SufficientBalance() {
        // Given
        Integer oldSavingsAccount = 1;
        BigDecimal savingsAccountBalance = BigDecimal.valueOf(20000000);
        BigDecimal amount = BigDecimal.valueOf(100000000);

        // When
        Boolean result =
                evaluationResultService.analyzeBalanceYearsRatio(oldSavingsAccount, savingsAccountBalance, amount);

        // Then
        assertThat(result).isEqualTo(true);
    }

    @Test
    void whenAnalyzeBalanceYearsRatio_NewAccount_InsufficientBalance() {
        // Given
        Integer oldSavingsAccount = 1;
        BigDecimal savingsAccountBalance = BigDecimal.valueOf(10000000);
        BigDecimal amount = BigDecimal.valueOf(100000000);

        // When
        Boolean result =
                evaluationResultService.analyzeBalanceYearsRatio(oldSavingsAccount, savingsAccountBalance, amount);

        // Then
        assertThat(result).isEqualTo(false);
    }

    @Test
    void whenAnalyzeBalanceYearsRatio_OldAccount_SufficientBalance() {
        // Given
        Integer oldSavingsAccount = 2;
        BigDecimal savingsAccountBalance = BigDecimal.valueOf(10000000);
        BigDecimal amount = BigDecimal.valueOf(100000000);

        // When
        Boolean result =
                evaluationResultService.analyzeBalanceYearsRatio(oldSavingsAccount, savingsAccountBalance, amount);

        // Then
        assertThat(result).isEqualTo(true);
    }

    @Test
    void whenAnalyzeBalanceYearsRatio_OldAccount_InsufficientBalance() {
        // Given
        Integer oldSavingsAccount = 2;
        BigDecimal savingsAccountBalance = BigDecimal.valueOf(5000000);
        BigDecimal amount = BigDecimal.valueOf(100000000);

        // When
        Boolean result =
                evaluationResultService.analyzeBalanceYearsRatio(oldSavingsAccount, savingsAccountBalance, amount);

        // Then
        assertThat(result).isEqualTo(false);
    }

    @Test
    void testAnalyzeRecentWithdrawals_WithinLimit() {
        // Given
        BigDecimal maximumWithdrawalInSixMonths = BigDecimal.valueOf(5000);
        BigDecimal savingsAccountBalance = BigDecimal.valueOf(20000000);

        // When
        Boolean result = evaluationResultService.analyzeRecentWithdrawals(maximumWithdrawalInSixMonths, savingsAccountBalance);

        // Then
        assertThat(result).isEqualTo(true);
    }

    @Test
    void testAnalyzeRecentWithdrawals_ExceedingLimit() {
        // Given
        BigDecimal maximumWithdrawalInSixMonths = BigDecimal.valueOf(6000000);
        BigDecimal savingsAccountBalance = BigDecimal.valueOf(20000000);

        // When
        Boolean result = evaluationResultService.analyzeRecentWithdrawals(maximumWithdrawalInSixMonths, savingsAccountBalance);

        // Then
        assertThat(result).isEqualTo(false);
    }
}
