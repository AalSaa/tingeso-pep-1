package com.example.PrestaBancoBackend.initializers;

import com.example.PrestaBancoBackend.entities.LoanTypeEntity;
import com.example.PrestaBancoBackend.repositories.LoanTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class LoanTypeInitializer implements CommandLineRunner {
    @Autowired
    private LoanTypeRepository loanTypeRepository;

    @Override
    public void run(String... args) {
        if (loanTypeRepository.count() == 0) {
            List<LoanTypeEntity> loanTypes = Arrays.asList(
                    LoanTypeEntity.builder()
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
                            .build(),
                    LoanTypeEntity.builder()
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
                            .build(),
                    LoanTypeEntity.builder()
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
                            .build(),
                    LoanTypeEntity.builder()
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
                            .build()
            );

            loanTypeRepository.saveAll(loanTypes);
        }
    }
}
