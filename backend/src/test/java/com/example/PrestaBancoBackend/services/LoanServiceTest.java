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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LoanServiceTest {
    @Mock
    private LoanRepository loanRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private LoanTypeRepository loanTypeRepository;

    @InjectMocks
    private LoanService loanService;

    private Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenGetAllLoans_HaveOneLoan() {
        // Given
        LoanEntity loan1 = LoanEntity.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(100))
                .build();

        // When
        when(loanRepository.findAll()).thenReturn(Arrays.asList(loan1));
        List<LoanEntity> loans = loanService.getAllLoans();

        // Then
        assertThat(loans).hasSize(1);
        assertThat(loans).contains(loan1);
    }

    @Test
    void whenGetAllLoans_HaveTwoLoans() {
        // Given
        LoanEntity loan1 = LoanEntity.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(100))
                .build();

        LoanEntity loan2 = LoanEntity.builder()
                .id(2L)
                .amount(BigDecimal.valueOf(200))
                .build();

        // When
        when(loanRepository.findAll()).thenReturn(Arrays.asList(loan1, loan2));
        List<LoanEntity> loans = loanService.getAllLoans();

        // Then
        assertThat(loans).hasSize(2);
        assertThat(loans).contains(loan1, loan2);
    }

    @Test
    void whenGetAllLoans_HaveThreeLoans() {
        // Given
        LoanEntity loan1 = LoanEntity.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(100))
                .build();

        LoanEntity loan2 = LoanEntity.builder()
                .id(2L)
                .amount(BigDecimal.valueOf(200))
                .build();

        LoanEntity loan3 = LoanEntity.builder()
                .id(3L)
                .amount(BigDecimal.valueOf(300))
                .build();

        // When
        when(loanRepository.findAll()).thenReturn(Arrays.asList(loan1, loan2, loan3));
        List<LoanEntity> loans = loanService.getAllLoans();

        // Then
        assertThat(loans).hasSize(3);
        assertThat(loans).contains(loan1, loan2, loan3);
    }

    @Test
    void whenGetAllLoans_HaveFourLoans() {
        // Given
        LoanEntity loan1 = LoanEntity.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(100))
                .build();

        LoanEntity loan2 = LoanEntity.builder()
                .id(2L)
                .amount(BigDecimal.valueOf(200))
                .build();

        LoanEntity loan3 = LoanEntity.builder()
                .id(3L)
                .amount(BigDecimal.valueOf(300))
                .build();

        LoanEntity loan4 = LoanEntity.builder()
                .id(4L)
                .amount(BigDecimal.valueOf(400))
                .build();

        // When
        when(loanRepository.findAll()).thenReturn(Arrays.asList(loan1, loan2, loan3, loan4));
        List<LoanEntity> loans = loanService.getAllLoans();

        // Then
        assertThat(loans).hasSize(4);
        assertThat(loans).contains(loan1, loan2, loan3, loan4);
    }

    @Test
    void whenGetAllLoans_DontHaveLoans() {
        // When
        when(loanRepository.findAll()).thenReturn(new ArrayList<>());
        List<LoanEntity> loans = loanService.getAllLoans();

        // Then
        assertThat(loans).isEqualTo(new ArrayList<LoanEntity>());
    }

    @Test
    void whenGetAllLoansByStatus_HaveOneLoanWithTheSameStatus() {
        // Given
        String status = "Revision inicial";

        LoanEntity loan1 = LoanEntity.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(100))
                .status(status)
                .build();

        // When
        when(loanRepository.findAllByStatus(status)).thenReturn(Arrays.asList(loan1));
        List<LoanEntity> loans = loanService.getAllLoansByStatus(status);

        // Then
        assertThat(loans).hasSize(1);
        assertThat(loans).contains(loan1);
    }

    @Test
    void whenGetAllLoansByStatus_HaveTwoLoansButOnlyOneWithSameStatus() {
        // Given
        String status = "Revision inicial";

        LoanEntity loan1 = LoanEntity.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(100))
                .status(status)
                .build();

        LoanEntity loan2 = LoanEntity.builder()
                .id(2L)
                .amount(BigDecimal.valueOf(200))
                .status("Simulacion")
                .build();

        // When
        when(loanRepository.findAllByStatus(status)).thenReturn(Arrays.asList(loan1));
        List<LoanEntity> loans = loanService.getAllLoansByStatus(status);

        // Then
        assertThat(loans).hasSize(1);
        assertThat(loans).contains(loan1);
    }

    @Test
    void whenGetAllLoansByStatus_HaveThreeLoansButOnlyTwoWithSameStatus() {
        // Given
        String status = "Revision inicial";

        LoanEntity loan1 = LoanEntity.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(100))
                .status(status)
                .build();

        LoanEntity loan2 = LoanEntity.builder()
                .id(2L)
                .amount(BigDecimal.valueOf(200))
                .status("Simulacion")
                .build();

        LoanEntity loan3 = LoanEntity.builder()
                .id(3L)
                .amount(BigDecimal.valueOf(300))
                .status(status)
                .build();

        // When
        when(loanRepository.findAllByStatus(status)).thenReturn(Arrays.asList(loan1, loan3));
        List<LoanEntity> loans = loanService.getAllLoansByStatus(status);

        // Then
        assertThat(loans).hasSize(2);
        assertThat(loans).contains(loan1, loan3);
    }

    @Test
    void whenGetAllLoansByStatus_HaveFourLoansButNoneWithSameStatus() {
        // Given
        String status = "Revision inicial";

        LoanEntity loan1 = LoanEntity.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(100))
                .status("Simulacion")
                .build();

        LoanEntity loan2 = LoanEntity.builder()
                .id(2L)
                .amount(BigDecimal.valueOf(200))
                .status("Simulacion")
                .build();

        LoanEntity loan3 = LoanEntity.builder()
                .id(3L)
                .amount(BigDecimal.valueOf(300))
                .status("Simulacion")
                .build();

        LoanEntity loan4 = LoanEntity.builder()
                .id(4L)
                .amount(BigDecimal.valueOf(400))
                .status("Simulacion")
                .build();

        // When
        when(loanRepository.findAllByStatus(status)).thenReturn(new ArrayList<>());
        List<LoanEntity> loans = loanService.getAllLoansByStatus(status);

        // Then
        assertThat(loans).isEqualTo(new ArrayList<LoanEntity>());
    }

    @Test
    void whenGetAllLoansByStatus_DontHaveLoans() {
        // Given
        String status = "Revision inicial";

        // When
        when(loanRepository.findAllByStatus(status)).thenReturn(new ArrayList<>());
        List<LoanEntity> loans = loanService.getAllLoansByStatus(status);

        // Then
        assertThat(loans).isEqualTo(new ArrayList<LoanEntity>());
    }

    @Test
    void whenGetAllLoansByStatusNot_HaveOneLoanWithNotTheSameStatus() {
        // Given
        String status = "Revision inicial";

        LoanEntity loan1 = LoanEntity.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(100))
                .status("Simulacion")
                .build();

        // When
        when(loanRepository.findAllByStatusNot(status)).thenReturn(Arrays.asList(loan1));
        List<LoanEntity> loans = loanService.getAllLoansByStatusNot(status);

        // Then
        assertThat(loans).hasSize(1);
        assertThat(loans).contains(loan1);
    }

    @Test
    void whenGetAllLoansByStatusNot_HaveOneLoanWithTheSameStatus() {
        // Given
        String status = "Revision inicial";

        LoanEntity loan1 = LoanEntity.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(100))
                .status(status)
                .build();

        // When
        when(loanRepository.findAllByStatusNot(status)).thenReturn(new ArrayList<>());
        List<LoanEntity> loans = loanService.getAllLoansByStatusNot(status);

        // Then
        assertThat(loans).isEqualTo(new ArrayList<LoanEntity>());
    }

    @Test
    void whenGetAllLoansByStatusNot_HaveTwoLoansButOnlyOneWithTheSameStatus() {
        // Given
        String status = "Revision inicial";

        LoanEntity loan1 = LoanEntity.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(100))
                .status(status)
                .build();

        LoanEntity loan2 = LoanEntity.builder()
                .id(2L)
                .amount(BigDecimal.valueOf(200))
                .status("Simulacion")
                .build();

        // When
        when(loanRepository.findAllByStatusNot(status)).thenReturn(Arrays.asList(loan2));
        List<LoanEntity> loans = loanService.getAllLoansByStatusNot(status);

        // Then
        assertThat(loans).hasSize(1);
        assertThat(loans).contains(loan2);
    }

    @Test
    void whenGetAllLoansByStatusNot_HaveTwoLoansButNoneWithTheSameStatus() {
        // Given
        String status = "Revision inicial";

        LoanEntity loan1 = LoanEntity.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(100))
                .status("Simulacion")
                .build();

        LoanEntity loan2 = LoanEntity.builder()
                .id(2L)
                .amount(BigDecimal.valueOf(200))
                .status("Simulacion")
                .build();

        // When
        when(loanRepository.findAllByStatusNot(status)).thenReturn(Arrays.asList(loan1, loan2));
        List<LoanEntity> loans = loanService.getAllLoansByStatusNot(status);

        // Then
        assertThat(loans).hasSize(2);
        assertThat(loans).contains(loan1, loan2);
    }

    @Test
    void whenGetAllLoansByStatusNot_DontHaveLoans() {
        // Given
        String status = "Revision inicial";

        // When
        when(loanRepository.findAllByStatusNot(status)).thenReturn(new ArrayList<>());
        List<LoanEntity> loans = loanService.getAllLoansByStatusNot(status);

        // Then
        assertThat(loans).isEmpty();
    }

    @Test
    void whenGetAllLoansByUserIdAndStatusNot_HaveOneLoanWithUserAndNotTheSameStatus() {
        // Given
        UserEntity user1 = UserEntity.builder()
                .id(1L)
                .firstName("John")
                .build();

        String status = "Revision inicial";

        LoanEntity loan1 = LoanEntity.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(100))
                .status("Simulacion")
                .user(user1)
                .build();

        // When
        when(loanRepository.findAllByUserIdAndStatusNot(user1.getId(), status)).thenReturn(Arrays.asList(loan1));
        List<LoanEntity> loans = loanService.getAllLoansByUserIdAndStatusNot(user1.getId(), status);

        // Then
        assertThat(loans).hasSize(1);
        assertThat(loans).contains(loan1);
    }

    @Test
    void whenGetAllLoansByUserIdAndStatusNot_HaveTwoLoansButOnlyOneWithUserAndNotTheSameStatus() {
        // Given
        UserEntity user1 = UserEntity.builder()
                .id(1L)
                .firstName("John")
                .build();

        String status = "Revision inicial";

        LoanEntity loan1 = LoanEntity.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(100))
                .status("Simulacion")
                .user(user1)
                .build();

        LoanEntity loan2 = LoanEntity.builder()
                .id(2L)
                .amount(BigDecimal.valueOf(200))
                .status(status)
                .user(user1)
                .build();

        // When
        when(loanRepository.findAllByUserIdAndStatusNot(user1.getId(), status)).thenReturn(Arrays.asList(loan1));
        List<LoanEntity> loans = loanService.getAllLoansByUserIdAndStatusNot(user1.getId(), status);

        // Then
        assertThat(loans).hasSize(1);
        assertThat(loans).contains(loan1);
    }

    @Test
    void whenGetAllLoansByUserIdAndStatusNot_HaveThreeLoansButOnlyOneWithUserAndNotTheSameStatus() {
        // Given
        UserEntity user1 = UserEntity.builder()
                .id(1L)
                .firstName("John")
                .build();

        UserEntity user2 = UserEntity.builder()
                .id(2L)
                .firstName("Juan")
                .build();

        String status = "Revision inicial";

        LoanEntity loan1 = LoanEntity.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(100))
                .status("Simulacion")
                .user(user1)
                .build();

        LoanEntity loan2 = LoanEntity.builder()
                .id(2L)
                .amount(BigDecimal.valueOf(200))
                .status(status)
                .user(user1)
                .build();

        LoanEntity loan3 = LoanEntity.builder()
                .id(3L)
                .amount(BigDecimal.valueOf(300))
                .status("Simulacion")
                .user(user2)
                .build();

        // When
        when(loanRepository.findAllByUserIdAndStatusNot(user1.getId(), status)).thenReturn(Arrays.asList(loan1));
        List<LoanEntity> loans = loanService.getAllLoansByUserIdAndStatusNot(user1.getId(), status);

        // Then
        assertThat(loans).hasSize(1);
        assertThat(loans).contains(loan1);
    }

    @Test
    void whenGetAllLoansByUserIdAndStatusNot_HaveThreeLoansButNoneWithUserAndNotTheSameStatus() {
        // Given
        UserEntity user1 = UserEntity.builder()
                .id(1L)
                .firstName("John")
                .build();

        UserEntity user2 = UserEntity.builder()
                .id(2L)
                .firstName("Juan")
                .build();

        String status = "Revision inicial";

        LoanEntity loan1 = LoanEntity.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(100))
                .status("Simulacion")
                .user(user2)
                .build();

        LoanEntity loan2 = LoanEntity.builder()
                .id(2L)
                .amount(BigDecimal.valueOf(200))
                .status(status)
                .user(user1)
                .build();

        LoanEntity loan3 = LoanEntity.builder()
                .id(3L)
                .amount(BigDecimal.valueOf(300))
                .status("Simulacion")
                .user(user2)
                .build();

        // When
        when(loanRepository.findAllByUserIdAndStatusNot(user1.getId(), status)).thenReturn(new ArrayList<>());
        List<LoanEntity> loans = loanService.getAllLoansByUserIdAndStatusNot(user1.getId(), status);

        // Then
        assertThat(loans).isEqualTo(new ArrayList<LoanEntity>());
    }

    @Test
    void whenGetAllLoansByUserIdAndStatusNot_DontHaveLoans() {
        // Given
        UserEntity user1 = UserEntity.builder()
                .id(1L)
                .firstName("John")
                .build();

        UserEntity user2 = UserEntity.builder()
                .id(2L)
                .firstName("Juan")
                .build();

        String status = "Revision inicial";

        // When
        when(loanRepository.findAllByUserIdAndStatusNot(user1.getId(), status)).thenReturn(new ArrayList<>());
        List<LoanEntity> loans = loanService.getAllLoansByUserIdAndStatusNot(user1.getId(), status);

        // Then
        assertThat(loans).isEqualTo(new ArrayList<LoanEntity>());
    }

    @Test
    void whenGetLoanById_LoanFound() {
        // Given
        Long loanId = 1L;

        LoanEntity loan = LoanEntity.builder()
                .id(loanId)
                .amount(BigDecimal.valueOf(100))
                .build();

        // When
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
        LoanEntity foundLoan = loanService.getLoanById(loanId);

        // Then
        assertThat(foundLoan).isNotNull();
        assertThat(foundLoan.getId()).isEqualTo(loanId);
        assertThat(foundLoan.getAmount()).isEqualTo(BigDecimal.valueOf(100));
    }

    @Test
    void whenGetLoanById_MultipleLoansExist() {
        // Given
        Long loanId = 1L;

        LoanEntity loan1 = LoanEntity.builder()
                .id(loanId)
                .amount(BigDecimal.valueOf(100))
                .build();

        LoanEntity loan2 = LoanEntity.builder()
                .id(2L)
                .amount(BigDecimal.valueOf(200))
                .build();

        // When
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan1));
        LoanEntity foundLoan = loanService.getLoanById(loanId);

        assertThat(foundLoan).isNotNull();
        assertThat(foundLoan.getId()).isEqualTo(loanId);
        assertThat(foundLoan).isNotEqualTo(loan2);
    }

    @Test
    void whenGetLoanById_LoanNotFound() {
        // Given
        Long loanId = 1L;

        // When
        when(loanRepository.findById(loanId)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> loanService.getLoanById(loanId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Loan not found");
    }

    @Test
    void whenGetLoanById_IdIsNull() {
        // When
        when(loanRepository.findById(null)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> loanService.getLoanById(null))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Loan not found");
    }

    @Test
    void whenGetLoanById_IdIsNegative() {
        // Given
        Long invalidId = -1L;

        // When
        when(loanRepository.findById(invalidId)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> loanService.getLoanById(invalidId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Loan not found");
    }

    @Test
    void whenCreateLoan_LoanIsCreated() {
        // Given
        Long userId = 1L;
        UserEntity user = UserEntity.builder()
                .id(userId)
                .firstName("John")
                .status("Active")
                .build();

        Long loanTypeId = 2L;
        LoanTypeEntity loanType = LoanTypeEntity.builder()
                .id(loanTypeId)
                .name("Primera Vivienda")
                .maxTerm(30)
                .minAnnualInterestRate(3.5)
                .maxAnnualInterestRate(5.0)
                .maxPercentageAmount(80)
                .typeOfDocumentsRequired(Arrays.asList(
                        "Comprobante de ingresos",
                        "Certificado de avalúo",
                        "Historial crediticio"
                ))
                .build();

        LoanDTO loanDTO = new LoanDTO(
                BigDecimal.valueOf(100000000),
                BigDecimal.valueOf(60000000),
                20,
                4.5,
                0.0,
                0.0,
                0.0,
                "In validation",
                userId,
                loanTypeId
        );

        // When
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(loanTypeRepository.findById(loanTypeId)).thenReturn(Optional.of(loanType));
        when(loanRepository.save(any(LoanEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        LoanEntity loan = loanService.createLoan(loanDTO);

        // Then
        assertThat(loan).isNotNull();
        assertThat(loan.getAmount()).isEqualTo(loanDTO.getAmount());
        assertThat(loan.getUser()).isEqualTo(user);
        assertThat(loan.getLoanType()).isEqualTo(loanType);
    }

    @Test
    void whenCreateLoan_UserNotFound() {
        // Given
        Long userId = 1L;

        Long loanTypeId = 2L;
        LoanTypeEntity loanType = LoanTypeEntity.builder()
                .id(loanTypeId)
                .name("Primera Vivienda")
                .maxTerm(30)
                .minAnnualInterestRate(3.5)
                .maxAnnualInterestRate(5.0)
                .maxPercentageAmount(80)
                .typeOfDocumentsRequired(Arrays.asList(
                        "Comprobante de ingresos",
                        "Certificado de avalúo",
                        "Historial crediticio"
                ))
                .build();

        LoanDTO loanDTO = new LoanDTO(
                BigDecimal.valueOf(100000000),
                BigDecimal.valueOf(60000000),
                20,
                4.5,
                0.0,
                0.0,
                0.0,
                "In validation",
                userId,
                loanTypeId
        );

        // When
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        when(loanTypeRepository.findById(loanTypeId)).thenReturn(Optional.of(loanType));
        when(loanRepository.save(any(LoanEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Then
        assertThatThrownBy(() -> loanService.createLoan(loanDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    void whenCreateLoan_LoanNotFound() {
        // Given
        Long userId = 1L;
        UserEntity user = UserEntity.builder()
                .id(userId)
                .firstName("John")
                .status("Active")
                .build();

        Long loanTypeId = 2L;

        LoanDTO loanDTO = new LoanDTO(
                BigDecimal.valueOf(100000000),
                BigDecimal.valueOf(60000000),
                20,
                4.5,
                0.0,
                0.0,
                0.0,
                "In validation",
                userId,
                loanTypeId
        );

        // When
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(loanTypeRepository.findById(loanTypeId)).thenReturn(Optional.empty());
        when(loanRepository.save(any(LoanEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Then
        assertThatThrownBy(() -> loanService.createLoan(loanDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Loan type not found");
    }

    @Test
    void whenCreateLoan_UserRestrictionsFail() {
        // Given
        Long userId = 1L;
        UserEntity user = UserEntity.builder()
                .id(userId)
                .firstName("John")
                .status("In validation")
                .build();

        Long loanTypeId = 2L;
        LoanTypeEntity loanType = LoanTypeEntity.builder()
                .id(loanTypeId)
                .name("Primera Vivienda")
                .maxTerm(30)
                .minAnnualInterestRate(3.5)
                .maxAnnualInterestRate(5.0)
                .maxPercentageAmount(80)
                .typeOfDocumentsRequired(Arrays.asList(
                        "Comprobante de ingresos",
                        "Certificado de avalúo",
                        "Historial crediticio"
                ))
                .build();

        LoanDTO loanDTO = new LoanDTO(
                BigDecimal.valueOf(100000000),
                BigDecimal.valueOf(60000000),
                20,
                4.5,
                0.0,
                0.0,
                0.0,
                "In validation",
                userId,
                loanTypeId
        );

        // When
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(loanTypeRepository.findById(loanTypeId)).thenReturn(Optional.of(loanType));

        // Then
        assertThatThrownBy(() -> loanService.createLoan(loanDTO))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("User is not active");
    }

    @Test
    void whenCreateLoan_LoanRestrictionsFail() {
        // Given
        Long userId = 1L;
        UserEntity user = UserEntity.builder()
                .id(userId)
                .firstName("John")
                .status("Active")
                .build();

        Long loanTypeId = 2L;
        LoanTypeEntity loanType = LoanTypeEntity.builder()
                .id(loanTypeId)
                .name("Primera Vivienda")
                .maxTerm(30)
                .minAnnualInterestRate(3.5)
                .maxAnnualInterestRate(5.0)
                .maxPercentageAmount(80)
                .typeOfDocumentsRequired(Arrays.asList(
                        "Comprobante de ingresos",
                        "Certificado de avalúo",
                        "Historial crediticio"
                ))
                .build();

        LoanDTO loanDTO = new LoanDTO(
                BigDecimal.valueOf(100000000),
                BigDecimal.valueOf(100000000),
                20,
                4.5,
                0.0,
                0.0,
                0.0,
                "In validation",
                userId,
                loanTypeId
        );

        // When
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(loanTypeRepository.findById(loanTypeId)).thenReturn(Optional.of(loanType));

        // Then
        assertThatThrownBy(() -> loanService.createLoan(loanDTO))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Amount exceeds max allowed amount");
    }

    @Test
    void whenUpdateLoan_LoanisUpdated() {
        // Given
        Long userId = 1L;
        UserEntity user = UserEntity.builder()
                .id(userId)
                .firstName("John")
                .status("Active")
                .build();

        Long loanTypeId = 2L;
        LoanTypeEntity loanType = LoanTypeEntity.builder()
                .id(loanTypeId)
                .name("Primera Vivienda")
                .maxTerm(30)
                .minAnnualInterestRate(3.5)
                .maxAnnualInterestRate(5.0)
                .maxPercentageAmount(80)
                .typeOfDocumentsRequired(Arrays.asList(
                        "Comprobante de ingresos",
                        "Certificado de avalúo",
                        "Historial crediticio"
                ))
                .build();

        Long loanId = 3L;

        LoanEntity existingLoan = LoanEntity.builder()
                .id(loanId)
                .propertyValue(BigDecimal.valueOf(100000000))
                .amount(BigDecimal.valueOf(60000000))
                .termInYears(20)
                .annualInterestRatePercentage(4.5)
                .monthlyLifeInsurancePercentage(0.0)
                .monthlyFireInsuranceAmountPercentage(0.0)
                .administrationFeePercentage(0.0)
                .status("Revision Inicial")
                .user(user)
                .loanType(loanType)
                .build();

        LoanUpdateDTO loanDTO = new LoanUpdateDTO(
                BigDecimal.valueOf(100000000),
                BigDecimal.valueOf(60000000),
                20,
                4.5,
                0.0,
                0.0,
                0.0,
                "En evaluacion"
        );

        // When
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(existingLoan));
        when(loanRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        LoanEntity updatedLoan = loanService.updateLoan(loanId, loanDTO);

        // Then
        assertThat(updatedLoan).isNotNull();
        assertThat(updatedLoan.getId()).isEqualTo(loanId);
        assertThat(updatedLoan.getUser()).isEqualTo(existingLoan.getUser());
        assertThat(updatedLoan.getLoanType()).isEqualTo(existingLoan.getLoanType());
    }

    @Test
    void whenUpdateLoan_LoanNotFound() {
        // Given
        Long loanId = 3L;

        LoanUpdateDTO loanDTO = new LoanUpdateDTO(
                BigDecimal.valueOf(100000000),
                BigDecimal.valueOf(60000000),
                20,
                4.5,
                0.0,
                0.0,
                0.0,
                "En evaluacion"
        );

        // When
        when(loanRepository.findById(loanId)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> loanService.updateLoan(loanId, loanDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Loan not found");

    }

    @Test
    void whenUpdateLoan_LoanRestrictionsFail() {
        // Given
        Long userId = 1L;
        UserEntity user = UserEntity.builder()
                .id(userId)
                .firstName("John")
                .status("Active")
                .build();

        Long loanTypeId = 2L;
        LoanTypeEntity loanType = LoanTypeEntity.builder()
                .id(loanTypeId)
                .name("Primera Vivienda")
                .maxTerm(30)
                .minAnnualInterestRate(3.5)
                .maxAnnualInterestRate(5.0)
                .maxPercentageAmount(80)
                .typeOfDocumentsRequired(Arrays.asList(
                        "Comprobante de ingresos",
                        "Certificado de avalúo",
                        "Historial crediticio"
                ))
                .build();

        Long loanId = 3L;
        LoanEntity existingLoan = LoanEntity.builder()
                .id(loanId)
                .propertyValue(BigDecimal.valueOf(100000000))
                .amount(BigDecimal.valueOf(60000000))
                .termInYears(20)
                .annualInterestRatePercentage(4.5)
                .monthlyLifeInsurancePercentage(0.0)
                .monthlyFireInsuranceAmountPercentage(0.0)
                .administrationFeePercentage(0.0)
                .status("Revision Inicial")
                .user(user)
                .loanType(loanType)
                .build();

        LoanUpdateDTO loanDTO = new LoanUpdateDTO(
                BigDecimal.valueOf(100000000),
                BigDecimal.valueOf(100000000),
                20,
                4.5,
                0.0,
                0.0,
                0.0,
                "En evaluacion"
        );

        // When
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(existingLoan));

        // Then
        assertThatThrownBy(() -> loanService.updateLoan(loanId, loanDTO))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Amount exceeds max allowed amount");
    }

    @Test
    void whenUpdateLoan_AllCampsAreNullOrEmpty() {
        // Given
        Long userId = 1L;
        UserEntity user = UserEntity.builder()
                .id(userId)
                .firstName("John")
                .status("Active")
                .build();

        Long loanTypeId = 2L;
        LoanTypeEntity loanType = LoanTypeEntity.builder()
                .id(loanTypeId)
                .name("Primera Vivienda")
                .maxTerm(30)
                .minAnnualInterestRate(3.5)
                .maxAnnualInterestRate(5.0)
                .maxPercentageAmount(80)
                .typeOfDocumentsRequired(Arrays.asList(
                        "Comprobante de ingresos",
                        "Certificado de avalúo",
                        "Historial crediticio"
                ))
                .build();

        Long loanId = 3L;
        LoanEntity existingLoan = LoanEntity.builder()
                .id(loanId)
                .propertyValue(BigDecimal.valueOf(100000000))
                .amount(BigDecimal.valueOf(60000000))
                .termInYears(20)
                .annualInterestRatePercentage(4.5)
                .monthlyLifeInsurancePercentage(0.0)
                .monthlyFireInsuranceAmountPercentage(0.0)
                .administrationFeePercentage(0.0)
                .status("Revision Inicial")
                .user(user)
                .loanType(loanType)
                .build();

        LoanUpdateDTO loanDTO = new LoanUpdateDTO(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                ""
        );

        // When
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(existingLoan));
        Set<ConstraintViolation<LoanUpdateDTO>> violations = validator.validate(loanDTO);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals(8, violations.size());
        for(ConstraintViolation<LoanUpdateDTO> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();

            switch (propertyPath) {
                case "propertyValue":
                    assertEquals("Property value is required", message);
                    break;
                case "amount":
                    assertEquals("Amount is required", message);
                    break;
                case "termInYears":
                    assertEquals("Term is required", message);
                    break;
                case "annualInterestRatePercentage":
                    assertEquals("Annual interest percentage rate is required", message);
                    break;
                case "monthlyLifeInsurancePercentage":
                    assertEquals("Monthly life insurance percentage is required", message);
                    break;
                case "monthlyFireInsuranceAmountPercentage":
                    assertEquals("Monthly fire insurance percentage is required", message);
                    break;
                case "administrationFeePercentage":
                    assertEquals("Administration fee percentage is required", message);
                    break;
                case "status":
                    assertEquals("Status is required", message);
                    break;
                default:
                    fail("Unexpected violation for field: " + propertyPath);
            }
        }
    }

    @Test
    void whenUpdateLoan_AmountIsNegative() {
        // Given
        Long userId = 1L;
        UserEntity user = UserEntity.builder()
                .id(userId)
                .firstName("John")
                .status("Active")
                .build();

        Long loanTypeId = 2L;
        LoanTypeEntity loanType = LoanTypeEntity.builder()
                .id(loanTypeId)
                .name("Primera Vivienda")
                .maxTerm(30)
                .minAnnualInterestRate(3.5)
                .maxAnnualInterestRate(5.0)
                .maxPercentageAmount(80)
                .typeOfDocumentsRequired(Arrays.asList(
                        "Comprobante de ingresos",
                        "Certificado de avalúo",
                        "Historial crediticio"
                ))
                .build();

        Long loanId = 3L;
        LoanEntity existingLoan = LoanEntity.builder()
                .id(loanId)
                .propertyValue(BigDecimal.valueOf(100000000))
                .amount(BigDecimal.valueOf(60000000))
                .termInYears(20)
                .annualInterestRatePercentage(4.5)
                .monthlyLifeInsurancePercentage(0.0)
                .monthlyFireInsuranceAmountPercentage(0.0)
                .administrationFeePercentage(0.0)
                .status("Revision Inicial")
                .user(user)
                .loanType(loanType)
                .build();

        LoanUpdateDTO loanDTO = new LoanUpdateDTO(
                BigDecimal.valueOf(100000000),
                BigDecimal.valueOf(-100000000),
                20,
                4.5,
                0.0,
                0.0,
                0.0,
                "En evaluacion"
        );

        // When
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(existingLoan));
        Set<ConstraintViolation<LoanUpdateDTO>> violations = validator.validate(loanDTO);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        ConstraintViolation<LoanUpdateDTO> violation = violations.iterator().next();
        assertEquals("amount", violation.getPropertyPath().toString());
        assertEquals("Amount must be greater than or equal to zero", violation.getMessage());
    }

    @Test
    void whenDeleteLoanById_LoanDeleted() {
        // Given
        Long loanId = 1L;

        // When
        when(loanRepository.existsById(loanId)).thenReturn(true);
        loanService.deleteLoanById(loanId);

        // Then
        verify(loanRepository, times(1)).deleteById(loanId);
    }

    @Test
    void whenDeleteLoanById_LoanNotFound() {
        // Given
        Long loanId = 1L;

        // When
        when(loanRepository.existsById(loanId)).thenReturn(false);

        // Then
        assertThatThrownBy(() -> loanService.deleteLoanById(loanId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Loan not found");
    }

    @Test
    void whenDeleteLoanById_IdIsNull() {
        // When & Then
        assertThatThrownBy(() -> loanService.deleteLoanById(null))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Loan not found");
    }

    @Test
    void whenDeleteUserById_LoanAlreadyDeleted() {
        // Given
        Long loanId = 1L;

        // When
        when(loanRepository.existsById(loanId)).thenReturn(true);
        loanService.deleteLoanById(loanId);
        when(loanRepository.existsById(loanId)).thenReturn(false);

        // Then
        assertThatThrownBy(() -> loanService.deleteLoanById(loanId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Loan not found");
    }

    @Test
    void whenDeleteUserById_UnexpectedErrorDuringDeletion() {
        // Given
        Long loanId = 1L;

        // When
        when(loanRepository.existsById(loanId)).thenReturn(true);
        doThrow(new RuntimeException("Unexpected error")).when(loanRepository).deleteById(loanId);

        // Then
        assertThatThrownBy(() -> loanService.deleteLoanById(loanId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Unexpected error");
    }

    @Test
    void whenVerifyUserRestrictions_UserNotActive() {
        // Given
        UserEntity user = UserEntity.builder()
                .id(1L)
                .firstName("John")
                .status("Inactive")
                .build();

        // When & Then
        assertThatThrownBy(() -> loanService.verifyUserRestrictions(user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("User is not active");
    }

    @Test
    void whenVerifyUserRestrictions_UserDontHaveLoans() {
        // Given
        UserEntity user = UserEntity.builder()
                .id(1L)
                .firstName("John")
                .status("Active")
                .build();

        // When
        when(loanRepository.findAllByUser(user)).thenReturn(Collections.emptyList());

        // Then
        assertDoesNotThrow(() -> loanService.verifyUserRestrictions(user));
    }

    @Test
    void whenVerifyUserRestrictions_UserWithApprovedOrCancelledLoans() {
        // Given
        UserEntity user = UserEntity.builder()
                .id(1L)
                .firstName("John")
                .status("Active")
                .build();

        LoanEntity loan1 = LoanEntity.builder().status("Aprobada").build();
        LoanEntity loan2 = LoanEntity.builder().status("Cancelada").build();

        // When
        when(loanRepository.findAllByUser(user)).thenReturn(Arrays.asList(loan1, loan2));

        // Then
        assertDoesNotThrow(() -> loanService.verifyUserRestrictions(user));
    }

    @Test
    void whenVerifyUserRestrictions_UserIsActiveAndHasLoanInProcess() {
        // Given
        UserEntity user = UserEntity.builder()
                .id(1L)
                .firstName("John")
                .status("Active")
                .build();

        LoanEntity loanInProcess = LoanEntity.builder().status("En proceso").build();

        // When
        when(loanRepository.findAllByUser(user)).thenReturn(Arrays.asList(loanInProcess));

        // Then
        assertThatThrownBy(() -> loanService.verifyUserRestrictions(user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("The user already has a loan in process");
    }

    @Test
    void whenVerifyUserRestrictions_UserIsActiveAndHasMultipleLoansIncludingOneInProcess() {
        // Given
        UserEntity user = UserEntity.builder()
                .id(1L)
                .firstName("John")
                .status("Active")
                .build();

        LoanEntity approvedLoan = LoanEntity.builder().status("Aprobada").build();
        LoanEntity loanInProcess = LoanEntity.builder().status("En proceso").build();

        // When
        when(loanRepository.findAllByUser(user)).thenReturn(Arrays.asList(approvedLoan, loanInProcess));

        // Then
        assertThatThrownBy(() -> loanService.verifyUserRestrictions(user))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("The user already has a loan in process");
    }

    @Test
    void whenVerifyLoanTypeRestrictions_AmountAndTermWithinLimits() {
        // Given
        BigDecimal propertyValue = BigDecimal.valueOf(100000);
        BigDecimal amount = BigDecimal.valueOf(80000);
        Integer termInYears = 20;
        LoanTypeEntity loanType = LoanTypeEntity.builder()
                .maxPercentageAmount(80)
                .maxTerm(30)
                .build();

        // When & Then
        assertDoesNotThrow(() -> loanService.verifyLoanTypeRestrictions(propertyValue, amount, termInYears, loanType));
    }

    @Test
    void whenVerifyLoanTypeRestrictions_AmountExceedsMaxAllowed() {
        // Given
        BigDecimal propertyValue = BigDecimal.valueOf(100000);
        BigDecimal amount = BigDecimal.valueOf(90000);
        Integer termInYears = 20;
        LoanTypeEntity loanType = LoanTypeEntity.builder()
                .maxPercentageAmount(80)
                .maxTerm(30)
                .build();

        // When & Then
        assertThatThrownBy(() -> loanService.verifyLoanTypeRestrictions(propertyValue, amount, termInYears, loanType))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Amount exceeds max allowed amount");
    }

    @Test
    void whenVerifyLoanTypeRestrictions_TermExceedsMaxAllowed() {
        // Given
        BigDecimal propertyValue = BigDecimal.valueOf(100000);
        BigDecimal amount = BigDecimal.valueOf(80000);
        Integer termInYears = 35;
        LoanTypeEntity loanType = LoanTypeEntity.builder()
                .maxPercentageAmount(80)
                .maxTerm(30)
                .build();

        // When & Then
        assertThatThrownBy(() -> loanService.verifyLoanTypeRestrictions(propertyValue, amount, termInYears, loanType))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("The term limit exceeded");
    }

    @Test
    void whenVerifyLoanTypeRestrictions_PropertyValueIsZero() {
        // Given
        BigDecimal propertyValue = BigDecimal.ZERO;
        BigDecimal amount = BigDecimal.valueOf(50000);
        Integer termInYears = 20;
        LoanTypeEntity loanType = LoanTypeEntity.builder()
                .maxPercentageAmount(80)
                .maxTerm(30)
                .build();

        // When & Then
        assertThatThrownBy(() -> loanService.verifyLoanTypeRestrictions(propertyValue, amount, termInYears, loanType))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Amount exceeds max allowed amount");
    }

    @Test
    void whenVerifyLoanTypeRestrictions_PropertyValueIsNegative() {
        // Given
        BigDecimal propertyValue = BigDecimal.valueOf(-100000);
        BigDecimal amount = BigDecimal.valueOf(50000);
        Integer termInYears = 20;
        LoanTypeEntity loanType = LoanTypeEntity.builder()
                .maxPercentageAmount(80)
                .maxTerm(30)
                .build();

        // When & Then
        assertThatThrownBy(() -> loanService.verifyLoanTypeRestrictions(propertyValue, amount, termInYears, loanType))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Amount exceeds max allowed amount");
    }
}