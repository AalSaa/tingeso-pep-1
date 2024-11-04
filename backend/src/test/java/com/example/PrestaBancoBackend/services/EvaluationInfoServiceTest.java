package com.example.PrestaBancoBackend.services;

import com.example.PrestaBancoBackend.dtos.EvaluationInfoDTO;
import com.example.PrestaBancoBackend.dtos.EvaluationInfoUpdateDTO;
import com.example.PrestaBancoBackend.entities.EvaluationInfoEntity;
import com.example.PrestaBancoBackend.entities.LoanEntity;
import com.example.PrestaBancoBackend.repositories.EvaluationInfoRepository;
import com.example.PrestaBancoBackend.repositories.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EvaluationInfoServiceTest {
    @Mock
    EvaluationInfoRepository evaluationInfoRepository;
    @Mock
    LoanRepository loanRepository;

    @InjectMocks
    EvaluationInfoService evaluationInfoService;

    Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenGetAllEvaluationInfo_HaveOneEvaluationInfo() {
        // Given
        EvaluationInfoEntity evaluationInfo1 = EvaluationInfoEntity.builder()
                .id(1L)
                .monthlyIncome(BigDecimal.valueOf(2000000))
                .build();

        // When
        when(evaluationInfoRepository.findAll()).thenReturn(Arrays.asList(evaluationInfo1));
        List<EvaluationInfoEntity> evaluationInfos = evaluationInfoService.getAllEvaluationInfo();

        // Then
        assertThat(evaluationInfos).hasSize(1);
        assertThat(evaluationInfos).contains(evaluationInfo1);
    }

    @Test
    void whenGetAllEvaluationInfo_HaveTwoEvaluationInfos() {
        // Given
        EvaluationInfoEntity evaluationInfo1 = EvaluationInfoEntity.builder()
                .id(1L)
                .monthlyIncome(BigDecimal.valueOf(2000000))
                .build();

        EvaluationInfoEntity evaluationInfo2 = EvaluationInfoEntity.builder()
                .id(2L)
                .monthlyIncome(BigDecimal.valueOf(3000000))
                .build();

        // When
        when(evaluationInfoRepository.findAll()).thenReturn(Arrays.asList(evaluationInfo1, evaluationInfo2));
        List<EvaluationInfoEntity> evaluationInfos = evaluationInfoService.getAllEvaluationInfo();

        // Then
        assertThat(evaluationInfos).hasSize(2);
        assertThat(evaluationInfos).contains(evaluationInfo1, evaluationInfo2);
    }

    @Test
    void whenGetAllEvaluationInfo_HaveThreeEvaluationInfos() {
        // Given
        EvaluationInfoEntity evaluationInfo1 = EvaluationInfoEntity.builder()
                .id(1L)
                .monthlyIncome(BigDecimal.valueOf(2000000))
                .build();

        EvaluationInfoEntity evaluationInfo2 = EvaluationInfoEntity.builder()
                .id(2L)
                .monthlyIncome(BigDecimal.valueOf(3000000))
                .build();

        EvaluationInfoEntity evaluationInfo3 = EvaluationInfoEntity.builder()
                .id(3L)
                .monthlyIncome(BigDecimal.valueOf(3000000))
                .build();

        // When
        when(evaluationInfoRepository.findAll()).thenReturn(Arrays.asList(
                evaluationInfo1, evaluationInfo2, evaluationInfo3
        ));
        List<EvaluationInfoEntity> evaluationInfos = evaluationInfoService.getAllEvaluationInfo();

        // Then
        assertThat(evaluationInfos).hasSize(3);
        assertThat(evaluationInfos).contains(evaluationInfo1, evaluationInfo2, evaluationInfo3);
    }

    @Test
    void whenGetAllEvaluationInfo_HaveFourEvaluationInfos() {
        // Given
        EvaluationInfoEntity evaluationInfo1 = EvaluationInfoEntity.builder()
                .id(1L)
                .monthlyIncome(BigDecimal.valueOf(2000000))
                .build();

        EvaluationInfoEntity evaluationInfo2 = EvaluationInfoEntity.builder()
                .id(2L)
                .monthlyIncome(BigDecimal.valueOf(3000000))
                .build();

        EvaluationInfoEntity evaluationInfo3 = EvaluationInfoEntity.builder()
                .id(3L)
                .monthlyIncome(BigDecimal.valueOf(3000000))
                .build();

        EvaluationInfoEntity evaluationInfo4 = EvaluationInfoEntity.builder()
                .id(4L)
                .monthlyIncome(BigDecimal.valueOf(3000000))
                .build();

        // When
        when(evaluationInfoRepository.findAll()).thenReturn(Arrays.asList(
                evaluationInfo1, evaluationInfo2, evaluationInfo3, evaluationInfo4
        ));
        List<EvaluationInfoEntity> evaluationInfos = evaluationInfoService.getAllEvaluationInfo();

        // Then
        assertThat(evaluationInfos).hasSize(4);
        assertThat(evaluationInfos).contains(evaluationInfo1, evaluationInfo2, evaluationInfo3, evaluationInfo4);
    }

    @Test
    void whenGetAllEvaluationInfo_DontHaveEvaluationInfos() {
        // Given

        // When
        when(evaluationInfoRepository.findAll()).thenReturn(new ArrayList<>());
        List<EvaluationInfoEntity> evaluationInfos = evaluationInfoService.getAllEvaluationInfo();

        // Then
        assertThat(evaluationInfos).isEqualTo(new ArrayList<EvaluationInfoEntity>());
    }

    @Test
    void whenGetEvaluationInfoById_EvaluationInfoFound() {
        // Given
        Long evaluationInfoId = 1L;

        EvaluationInfoEntity evaluationInfo1 = EvaluationInfoEntity.builder()
                .id(evaluationInfoId)
                .monthlyIncome(BigDecimal.valueOf(2000000))
                .build();

        // When
        when(evaluationInfoRepository.findById(evaluationInfoId)).thenReturn(Optional.of(evaluationInfo1));
        EvaluationInfoEntity foundEvaluationInfo = evaluationInfoService.getEvaluationInfoById(evaluationInfoId);

        // Then
        assertThat(foundEvaluationInfo).isNotNull();
        assertThat(foundEvaluationInfo.getId()).isEqualTo(evaluationInfoId);
        assertThat(foundEvaluationInfo.getMonthlyIncome()).isEqualTo(BigDecimal.valueOf(2000000));
    }

    @Test
    void whenGetEvaluationInfoById_MultipleEvaluationInfosExits() {
        // Given
        Long evaluationInfoId = 1L;

        EvaluationInfoEntity evaluationInfo1 = EvaluationInfoEntity.builder()
                .id(evaluationInfoId)
                .monthlyIncome(BigDecimal.valueOf(2000000))
                .build();

        EvaluationInfoEntity evaluationInfo2 = EvaluationInfoEntity.builder()
                .id(2L)
                .monthlyIncome(BigDecimal.valueOf(3000000))
                .build();

        // When
        when(evaluationInfoRepository.findById(evaluationInfoId)).thenReturn(Optional.of(evaluationInfo1));
        EvaluationInfoEntity foundEvaluationInfo = evaluationInfoService.getEvaluationInfoById(evaluationInfoId);

        // Then
        assertThat(foundEvaluationInfo).isNotNull();
        assertThat(foundEvaluationInfo.getId()).isEqualTo(evaluationInfoId);
        assertThat(foundEvaluationInfo).isNotEqualTo(evaluationInfo2);
    }

    @Test
    void whenGetEvaluationInfoById_EvaluationInfoNotFound() {
        // Given
        Long evaluationInfoId = 1L;

        // When
        when(evaluationInfoRepository.findById(evaluationInfoId)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> evaluationInfoService.getEvaluationInfoById(evaluationInfoId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Evaluation info not found");
    }

    @Test
    void whenGetEvaluationInfoById_IdIsNull() {
        // When
        when(evaluationInfoRepository.findById(null)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> evaluationInfoService.getEvaluationInfoById(null))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Evaluation info not found");
    }

    @Test
    void whenGetEvaluationInfoById_IdIsNegative() {
        // Given
        Long invalidId = -1L;

        // When
        when(evaluationInfoRepository.findById(invalidId)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> evaluationInfoService.getEvaluationInfoById(invalidId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Evaluation info not found");
    }

    @Test
    void whenGetEvaluationInfoByLoanId_EvaluationInfoFound() {
        // Given
        Long loanId = 1L;

        LoanEntity loan = LoanEntity.builder()
                .id(loanId)
                .amount(BigDecimal.valueOf(100000000))
                .build();

        Long evaluationInfoId = 2L;

        EvaluationInfoEntity evaluationInfo1 = EvaluationInfoEntity.builder()
                .id(evaluationInfoId)
                .monthlyIncome(BigDecimal.valueOf(2000000))
                .loan(loan)
                .build();

        // When
        when(evaluationInfoRepository.findByLoanId(loanId)).thenReturn(Optional.of(evaluationInfo1));
        EvaluationInfoEntity foundEvaluationInfo = evaluationInfoService.getEvaluationInfoByLoanId(loanId);

        // Then
        assertThat(foundEvaluationInfo).isNotNull();
        assertThat(foundEvaluationInfo.getId()).isEqualTo(evaluationInfoId);
        assertThat(foundEvaluationInfo.getMonthlyIncome()).isEqualTo(BigDecimal.valueOf(2000000));
    }

    @Test
    void whenGetEvaluationInfoByLoanId_MultipleEvaluationInfosExits() {
        // Given
        Long loanId = 1L;

        LoanEntity loan = LoanEntity.builder()
                .id(loanId)
                .amount(BigDecimal.valueOf(100000000))
                .build();

        Long evaluationInfoId = 2L;

        EvaluationInfoEntity evaluationInfo1 = EvaluationInfoEntity.builder()
                .id(evaluationInfoId)
                .monthlyIncome(BigDecimal.valueOf(2000000))
                .loan(loan)
                .build();

        EvaluationInfoEntity evaluationInfo2 = EvaluationInfoEntity.builder()
                .id(3L)
                .monthlyIncome(BigDecimal.valueOf(2000000))
                .build();

        // When
        when(evaluationInfoRepository.findByLoanId(loanId)).thenReturn(Optional.of(evaluationInfo1));
        EvaluationInfoEntity foundEvaluationInfo = evaluationInfoService.getEvaluationInfoByLoanId(loanId);

        // Then
        assertThat(foundEvaluationInfo).isNotNull();
        assertThat(foundEvaluationInfo.getId()).isEqualTo(evaluationInfoId);
        assertThat(foundEvaluationInfo).isNotEqualTo(evaluationInfo2);
    }

    @Test
    void whenGetEvaluationInfoByLoanId_EvaluationInfoNotFound() {
        // Given
        Long loanId = 1L;

        LoanEntity loan = LoanEntity.builder()
                .id(loanId)
                .amount(BigDecimal.valueOf(100000000))
                .build();

        // When
        when(evaluationInfoRepository.findByLoanId(loanId)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> evaluationInfoService.getEvaluationInfoByLoanId(loanId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Evaluation info not found");
    }

    @Test
    void whenGetEvaluationInfoByLoanId_LoanIdIsNull() {
        // When
        when(evaluationInfoRepository.findByLoanId(null)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> evaluationInfoService.getEvaluationInfoByLoanId(null))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Evaluation info not found");
    }

    @Test
    void whenGetEvaluationInfoByLoanId_LoanIsNegative() {
        Long invalidLoanId = -1L;

        // When
        when(evaluationInfoRepository.findByLoanId(invalidLoanId)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> evaluationInfoService.getEvaluationInfoByLoanId(invalidLoanId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Evaluation info not found");
    }

    @Test
    void whenSaveEvaluationInfo_EvaluationInfoSaved() {
        // Given
        Long loanId = 1L;

        LoanEntity loan = LoanEntity.builder()
                .id(loanId)
                .amount(BigDecimal.valueOf(100000000))
                .build();

        EvaluationInfoDTO evaluationInfoDTO = new EvaluationInfoDTO(
                BigDecimal.valueOf(2000000),
                true,
                "Dependiente",
                2,
                BigDecimal.valueOf(300000),
                BigDecimal.valueOf(20000000),
                true,
                true,
                BigDecimal.valueOf(1500000),
                3,
                BigDecimal.valueOf(50000),
                loanId
        );

        // When
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
        when(evaluationInfoRepository.save(any(EvaluationInfoEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        EvaluationInfoEntity savedEvaluationInfo = evaluationInfoService.saveEvaluationInfo(evaluationInfoDTO);

        // Then
        assertThat(savedEvaluationInfo).isNotNull();
        assertThat(savedEvaluationInfo.getLoan().getId()).isEqualTo(loanId);
        assertThat(savedEvaluationInfo.getMonthlyIncome()).isEqualTo(BigDecimal.valueOf(2000000));
    }

    @Test
    void whenSaveEvaluationInfo_LoanNotFound() {
        // Given
        Long loanId = 1L;

        EvaluationInfoDTO evaluationInfoDTO = new EvaluationInfoDTO(
                BigDecimal.valueOf(2000000),
                true,
                "Dependiente",
                2,
                BigDecimal.valueOf(300000),
                BigDecimal.valueOf(20000000),
                true,
                true,
                BigDecimal.valueOf(1500000),
                3,
                BigDecimal.valueOf(50000),
                loanId
        );

        // When
        when(loanRepository.findById(loanId)).thenReturn(Optional.empty());
        when(evaluationInfoRepository.save(any(EvaluationInfoEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Then
        assertThatThrownBy(() -> evaluationInfoService.saveEvaluationInfo(evaluationInfoDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Loan not found");
    }

    @Test
    void whenSaveEvaluationInfo_LoanIdIsNull() {
        // Given
        EvaluationInfoDTO evaluationInfoDTO = new EvaluationInfoDTO(
                BigDecimal.valueOf(2000000),
                true,
                "Dependiente",
                2,
                BigDecimal.valueOf(300000),
                BigDecimal.valueOf(20000000),
                true,
                true,
                BigDecimal.valueOf(1500000),
                3,
                BigDecimal.valueOf(50000),
                null
        );

        // When
        when(loanRepository.findById(null)).thenReturn(Optional.empty());
        when(evaluationInfoRepository.save(any(EvaluationInfoEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Then
        assertThatThrownBy(() -> evaluationInfoService.saveEvaluationInfo(evaluationInfoDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Loan not found");
    }

    @Test
    void whenSaveEvaluationInfo_AllCampsAreNullOrEmpty() {
        // Given
        EvaluationInfoDTO evaluationInfoDTO = new EvaluationInfoDTO(
                null,
                null,
                "",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        // When
        Set<ConstraintViolation<EvaluationInfoDTO>> violations = validator.validate(evaluationInfoDTO);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals(12, violations.size());
        for(ConstraintViolation<EvaluationInfoDTO> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();

            switch (propertyPath) {
                case "monthlyIncome":
                    assertEquals("Monthly income is required", message);
                    break;
                case "havePositiveCreditHistory":
                    assertEquals("Validation of positive credit history is required", message);
                    break;
                case "employmentType":
                    assertEquals("Employment type is required", message);
                    break;
                case "employmentSeniority":
                    assertEquals("Employment seniority is required", message);
                    break;
                case "monthlyDebt":
                    assertEquals("Monthly debt is required", message);
                    break;
                case "savingsAccountBalance":
                    assertEquals("Savings account balance is required", message);
                    break;
                case "hasConsistentSavingsHistory":
                    assertEquals("Validation of consistent savings history is required", message);
                    break;
                case "hasPeriodicDeposits":
                    assertEquals("Validation of periodic deposits is required", message);
                    break;
                case "sumOfDeposits":
                    assertEquals("Sum of deposits is required", message);
                    break;
                case "oldSavingsAccount":
                    assertEquals("Old savings account is required", message);
                    break;
                case "maximumWithdrawalInSixMonths":
                    assertEquals("Maximum withdrawal in six months is required", message);
                    break;
                case "loanId":
                    assertEquals("Loan id is required", message);
                    break;
                default:
                    fail("Unexpected violation for field: " + propertyPath);
            }
        }
    }

    @Test
    void whenSaveEvaluationInfo_MonthlyIncomeIsNegative() {
        // Given
        Long loanId = 1L;

        LoanEntity loan = LoanEntity.builder()
                .id(loanId)
                .amount(BigDecimal.valueOf(100000000))
                .build();

        EvaluationInfoDTO evaluationInfoDTO = new EvaluationInfoDTO(
                BigDecimal.valueOf(-2000000),
                true,
                "Dependiente",
                2,
                BigDecimal.valueOf(300000),
                BigDecimal.valueOf(20000000),
                true,
                true,
                BigDecimal.valueOf(1500000),
                3,
                BigDecimal.valueOf(50000),
                loanId
        );

        // When
        Set<ConstraintViolation<EvaluationInfoDTO>> violations = validator.validate(evaluationInfoDTO);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        ConstraintViolation<EvaluationInfoDTO> violation = violations.iterator().next();
        assertEquals("monthlyIncome", violation.getPropertyPath().toString());
        assertEquals("Monthly income must be greater than or equal to zero", violation.getMessage());
    }

    @Test
    void whenUpdateEvaluationInfo_EvaluationInfoUpdated() {
        // Given
        Long loanId = 1L;

        LoanEntity loan = LoanEntity.builder()
                .id(loanId)
                .amount(BigDecimal.valueOf(100000000))
                .build();

        Long evaluationInfoId = 2L;

        EvaluationInfoEntity evaluationInfo = EvaluationInfoEntity.builder()
                .id(evaluationInfoId)
                .monthlyIncome(BigDecimal.valueOf(20000000))
                .havePositiveCreditHistory(false)
                .employmentType("Dependiente")
                .employmentSeniority(2)
                .monthlyDebt(BigDecimal.valueOf(300000))
                .savingsAccountBalance(BigDecimal.valueOf(20000000))
                .hasPeriodicDeposits(false)
                .sumOfDeposits(BigDecimal.valueOf(1500000))
                .oldSavingsAccount(3)
                .maximumWithdrawalInSixMonths(BigDecimal.valueOf(50000))
                .loan(loan)
                .build();

        EvaluationInfoUpdateDTO evaluationInfoDTO = new EvaluationInfoUpdateDTO(
                BigDecimal.valueOf(2000000),
                true,
                "Dependiente",
                2,
                BigDecimal.valueOf(300000),
                BigDecimal.valueOf(20000000),
                true,
                true,
                BigDecimal.valueOf(1500000),
                3,
                BigDecimal.valueOf(50000)
        );

        // When
        when(evaluationInfoRepository.findById(evaluationInfoId)).thenReturn(Optional.of(evaluationInfo));
        when(evaluationInfoRepository.save(any(EvaluationInfoEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        EvaluationInfoEntity updatedEvaluationInfo =
                evaluationInfoService.updateEvaluationInfo(evaluationInfoId, evaluationInfoDTO);

        // Then
        assertThat(updatedEvaluationInfo).isNotNull();
        assertThat(updatedEvaluationInfo.getId()).isEqualTo(evaluationInfoId);
        assertThat(updatedEvaluationInfo.getLoan()).isEqualTo(loan);
        assertThat(updatedEvaluationInfo.getHavePositiveCreditHistory())
                .isNotEqualTo(evaluationInfo.getHavePositiveCreditHistory());
        assertThat(updatedEvaluationInfo.getHasPeriodicDeposits())
                .isNotEqualTo(evaluationInfo.getHasPeriodicDeposits());
    }

    @Test
    void whenUpdateEvaluationInfo_EvaluationInfoNotFound() {
        // Given
        Long evaluationInfoId = 2L;

        EvaluationInfoUpdateDTO evaluationInfoDTO = new EvaluationInfoUpdateDTO(
                BigDecimal.valueOf(2000000),
                true,
                "Dependiente",
                2,
                BigDecimal.valueOf(300000),
                BigDecimal.valueOf(20000000),
                true,
                true,
                BigDecimal.valueOf(1500000),
                3,
                BigDecimal.valueOf(50000)
        );

        // When
        when(evaluationInfoRepository.findById(evaluationInfoId)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> evaluationInfoService.updateEvaluationInfo(evaluationInfoId, evaluationInfoDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Evaluation info not found");
    }

    @Test
    void whenUpdateEvaluationInfo_AllCampsAreNullOrEmpty() {
        // Given
        EvaluationInfoUpdateDTO evaluationInfoDTO = new EvaluationInfoUpdateDTO(
                null,
                null,
                "",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        // When
        Set<ConstraintViolation<EvaluationInfoUpdateDTO>> violations = validator.validate(evaluationInfoDTO);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals(11, violations.size());
        for(ConstraintViolation<EvaluationInfoUpdateDTO> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();

            switch (propertyPath) {
                case "monthlyIncome":
                    assertEquals("Monthly income is required", message);
                    break;
                case "havePositiveCreditHistory":
                    assertEquals("Validation of positive credit history is required", message);
                    break;
                case "employmentType":
                    assertEquals("Employment type is required", message);
                    break;
                case "employmentSeniority":
                    assertEquals("Employment seniority is required", message);
                    break;
                case "monthlyDebt":
                    assertEquals("Monthly debt is required", message);
                    break;
                case "savingsAccountBalance":
                    assertEquals("Savings account balance is required", message);
                    break;
                case "hasConsistentSavingsHistory":
                    assertEquals("Validation of consistent savings history is required", message);
                    break;
                case "hasPeriodicDeposits":
                    assertEquals("Validation of periodic deposits is required", message);
                    break;
                case "sumOfDeposits":
                    assertEquals("Sum of deposits is required", message);
                    break;
                case "oldSavingsAccount":
                    assertEquals("Old savings account is required", message);
                    break;
                case "maximumWithdrawalInSixMonths":
                    assertEquals("Maximum withdrawal in six months is required", message);
                    break;
                default:
                    fail("Unexpected violation for field: " + propertyPath);
            }
        }
    }

    @Test
    void whenUpdateEvaluationInfo_SavingsAccountBalanceIsNegative() {
        EvaluationInfoUpdateDTO evaluationInfoDTO = new EvaluationInfoUpdateDTO(
                BigDecimal.valueOf(2000000),
                true,
                "Dependiente",
                2,
                BigDecimal.valueOf(300000),
                BigDecimal.valueOf(-20000000),
                true,
                true,
                BigDecimal.valueOf(1500000),
                3,
                BigDecimal.valueOf(50000)
        );

        // When
        Set<ConstraintViolation<EvaluationInfoUpdateDTO>> violations = validator.validate(evaluationInfoDTO);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        ConstraintViolation<EvaluationInfoUpdateDTO> violation = violations.iterator().next();
        assertEquals("savingsAccountBalance", violation.getPropertyPath().toString());
        assertEquals("Savings account balance must be greater than or equal to zero", violation.getMessage());
    }

    @Test
    void whenUpdateEvaluationInfo_SaveThrowsException() {
        // Given
        Long loanId = 1L;

        LoanEntity loan = LoanEntity.builder()
                .id(loanId)
                .amount(BigDecimal.valueOf(100000000))
                .build();

        Long evaluationInfoId = 2L;

        EvaluationInfoEntity evaluationInfo = EvaluationInfoEntity.builder()
                .id(evaluationInfoId)
                .monthlyIncome(BigDecimal.valueOf(20000000))
                .havePositiveCreditHistory(false)
                .employmentType("Dependiente")
                .employmentSeniority(2)
                .monthlyDebt(BigDecimal.valueOf(300000))
                .savingsAccountBalance(BigDecimal.valueOf(20000000))
                .hasPeriodicDeposits(false)
                .sumOfDeposits(BigDecimal.valueOf(1500000))
                .oldSavingsAccount(3)
                .maximumWithdrawalInSixMonths(BigDecimal.valueOf(50000))
                .loan(loan)
                .build();

        EvaluationInfoUpdateDTO evaluationInfoDTO = new EvaluationInfoUpdateDTO(
                BigDecimal.valueOf(2000000),
                true,
                "Dependiente",
                2,
                BigDecimal.valueOf(300000),
                BigDecimal.valueOf(20000000),
                true,
                true,
                BigDecimal.valueOf(1500000),
                3,
                BigDecimal.valueOf(50000)
        );

        // When
        when(evaluationInfoRepository.findById(evaluationInfoId)).thenReturn(Optional.of(evaluationInfo));
        when(evaluationInfoRepository.save(any(EvaluationInfoEntity.class)))
                .thenThrow(new RuntimeException("Database error") {});

        // Then
        assertThatThrownBy(() -> evaluationInfoService.updateEvaluationInfo(evaluationInfoId, evaluationInfoDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Database error");
    }
}
