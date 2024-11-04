package com.example.PrestaBancoBackend.services;

import com.example.PrestaBancoBackend.entities.LoanTypeEntity;
import com.example.PrestaBancoBackend.repositories.LoanTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class LoanTypeServiceTest {
    @Mock
    LoanTypeRepository loanTypeRepository;

    @InjectMocks
    LoanTypeService loanTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetAllLoanTypes_HaveOneLoanType() {
        // Given
        LoanTypeEntity loanType1 = LoanTypeEntity.builder()
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

        // When
        when(loanTypeRepository.findAll()).thenReturn(Arrays.asList(loanType1));
        List<LoanTypeEntity> loanTypes = loanTypeService.getAllLoanTypes();

        //Then
        assertThat(loanTypes).hasSize(1);
        assertThat(loanTypes).contains(loanType1);
    }

    @Test
    void whenGetAllLoanTypes_HaveTwoLoanTypes() {
        // Given
        LoanTypeEntity loanType1 = LoanTypeEntity.builder()
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

        LoanTypeEntity loanType2 = LoanTypeEntity.builder()
                .name("Segunda Vivienda")
                .maxTerm(20)
                .minAnnualInterestRate(4.0)
                .maxAnnualInterestRate(6.0)
                .maxPercentageAmount(70)
                .typeOfDocumentsRequired(Arrays.asList(
                        "Comprobante de ingresos",
                        "Certificado de avalúo",
                        "Escritura de la primera vivienda",
                        "Historial crediticio"
                ))
                .build();

        // When
        when(loanTypeRepository.findAll()).thenReturn(Arrays.asList(loanType1, loanType2));
        List<LoanTypeEntity> loanTypes = loanTypeService.getAllLoanTypes();

        // Then
        assertThat(loanTypes).hasSize(2);
        assertThat(loanTypes).contains(loanType1, loanType2);
    }

    @Test
    void whenGetAllLoanTypes_HaveThreeLoanTypes() {
        // Given
        LoanTypeEntity loanType1 = LoanTypeEntity.builder()
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

        LoanTypeEntity loanType2 = LoanTypeEntity.builder()
                .name("Segunda Vivienda")
                .maxTerm(20)
                .minAnnualInterestRate(4.0)
                .maxAnnualInterestRate(6.0)
                .maxPercentageAmount(70)
                .typeOfDocumentsRequired(Arrays.asList(
                        "Comprobante de ingresos",
                        "Certificado de avalúo",
                        "Escritura de la primera vivienda",
                        "Historial crediticio"
                ))
                .build();

        LoanTypeEntity loanType3 = LoanTypeEntity.builder()
                .name("Propiedades Comerciales")
                .maxTerm(25)
                .minAnnualInterestRate(5.0)
                .maxAnnualInterestRate(7.0)
                .maxPercentageAmount(60)
                .typeOfDocumentsRequired(Arrays.asList(
                        "Estado financiero del negocio",
                        "Comprobante de ingresos",
                        "Certificado de avalúo",
                        "Plan de negocios"
                ))
                .build();

        // When
        when(loanTypeRepository.findAll()).thenReturn(Arrays.asList(loanType1, loanType2, loanType3));
        List<LoanTypeEntity> loanTypes = loanTypeService.getAllLoanTypes();

        // Then
        assertThat(loanTypes).hasSize(3);
        assertThat(loanTypes).contains(loanType1, loanType2, loanType3);
    }

    @Test
    void whenGetAllLoanTypes_HaveFourLoanTypes() {
        // Given
        LoanTypeEntity loanType1 = LoanTypeEntity.builder()
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

        LoanTypeEntity loanType2 = LoanTypeEntity.builder()
                .name("Segunda Vivienda")
                .maxTerm(20)
                .minAnnualInterestRate(4.0)
                .maxAnnualInterestRate(6.0)
                .maxPercentageAmount(70)
                .typeOfDocumentsRequired(Arrays.asList(
                        "Comprobante de ingresos",
                        "Certificado de avalúo",
                        "Escritura de la primera vivienda",
                        "Historial crediticio"
                ))
                .build();

        LoanTypeEntity loanType3 = LoanTypeEntity.builder()
                .name("Propiedades Comerciales")
                .maxTerm(25)
                .minAnnualInterestRate(5.0)
                .maxAnnualInterestRate(7.0)
                .maxPercentageAmount(60)
                .typeOfDocumentsRequired(Arrays.asList(
                        "Estado financiero del negocio",
                        "Comprobante de ingresos",
                        "Certificado de avalúo",
                        "Plan de negocios"
                ))
                .build();

        LoanTypeEntity loanType4 = LoanTypeEntity.builder()
                .name("Remodelación")
                .maxTerm(15)
                .minAnnualInterestRate(4.5)
                .maxAnnualInterestRate(6.0)
                .maxPercentageAmount(50)
                .typeOfDocumentsRequired(Arrays.asList(
                        "Comprobante de ingresos",
                        "Presupuesto de la remodelación",
                        "Certificado de avalúo actualizado"
                ))
                .build();

        // When
        when(loanTypeRepository.findAll()).thenReturn(Arrays.asList(loanType1, loanType2, loanType3, loanType4));
        List<LoanTypeEntity> loanTypes = loanTypeService.getAllLoanTypes();

        // Then
        assertThat(loanTypes).hasSize(4);
        assertThat(loanTypes).contains(loanType1, loanType2, loanType3, loanType4);
    }

    @Test
    void whenGetAllLoanTypes_DontHaveLoanTypes() {
        // When
        when(loanTypeRepository.findAll()).thenReturn(new ArrayList<>());
        List<LoanTypeEntity> loanTypes = loanTypeService.getAllLoanTypes();

        // Then
        assertThat(loanTypes).isEqualTo(new ArrayList<LoanTypeEntity>());
    }

    @Test
    void whenGetLoanTypeById_LoanTypeIsFound() {
        // Given
        Long id = 1L;
        LoanTypeEntity loanType = LoanTypeEntity.builder()
                .id(id)
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

        // When
        when(loanTypeRepository.findById(id)).thenReturn(Optional.of(loanType));
        LoanTypeEntity foundedLoanType = loanTypeService.getLoanTypeById(id);

        // Then
        assertThat(foundedLoanType).isNotNull();
        assertThat(foundedLoanType.getId()).isEqualTo(id);
        assertThat(foundedLoanType.getName()).isEqualTo("Primera Vivienda");
    }

    @Test
    void whenGetLoanTypeById_MultipleLoanTypeExists() {
        // Given
        Long id = 1L;
        LoanTypeEntity loanType1 = LoanTypeEntity.builder()
                .id(id)
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

        LoanTypeEntity loanType2 = LoanTypeEntity.builder()
                .id(2L)
                .name("Segunda Vivienda")
                .maxTerm(20)
                .minAnnualInterestRate(4.0)
                .maxAnnualInterestRate(6.0)
                .maxPercentageAmount(70)
                .typeOfDocumentsRequired(Arrays.asList(
                        "Comprobante de ingresos",
                        "Certificado de avalúo",
                        "Escritura de la primera vivienda",
                        "Historial crediticio"
                ))
                .build();

        // When
        when(loanTypeRepository.findById(id)).thenReturn(Optional.of(loanType1));
        LoanTypeEntity foundedLoanType = loanTypeService.getLoanTypeById(id);

        // Then
        assertThat(foundedLoanType).isNotNull();
        assertThat(foundedLoanType.getId()).isNotEqualTo(loanType2.getId());
        assertThat(foundedLoanType.getName()).isEqualTo("Primera Vivienda");
    }

    @Test
    void whenGetLoanTypeById_LoanTypeIsNotFound() {
        // Given
        Long id = 1L;

        // When
        when(loanTypeRepository.findById(id)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> loanTypeService.getLoanTypeById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Loan type not found");
    }

    @Test
    void whenGetLoanTypeById_IdIsNull() {
        // When & Then
        assertThatThrownBy(() -> loanTypeService.getLoanTypeById(null))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Loan type not found");
    }

    @Test
    void whenGetLoanTypeById_IdIsNegative() {
        // Given
        Long invalidId = 1L;

        // When
        when(loanTypeRepository.findById(invalidId)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> loanTypeService.getLoanTypeById(invalidId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Loan type not found");
    }
}
