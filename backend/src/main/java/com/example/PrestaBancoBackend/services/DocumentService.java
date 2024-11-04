package com.example.PrestaBancoBackend.services;

import com.example.PrestaBancoBackend.entities.DocumentEntity;
import com.example.PrestaBancoBackend.entities.LoanEntity;
import com.example.PrestaBancoBackend.repositories.DocumentRepository;
import com.example.PrestaBancoBackend.repositories.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private LoanRepository loanRepository;

    public List<DocumentEntity> getAllDocuments() {
        return documentRepository.findAll();
    }

    public List<DocumentEntity> getAllDocumentsByLoanId(Long loanId) {
        Optional<LoanEntity> possibleLoan = loanRepository.findById(loanId);

        if (possibleLoan.isEmpty()) {
            throw new EntityNotFoundException("Loan not found");
        }

        return documentRepository.findAllByLoanId(loanId);
    }

    public DocumentEntity getDocumentById(Long id) {
        Optional<DocumentEntity> document = documentRepository.findById(id);

        if (document.isEmpty()) {
            throw new EntityNotFoundException("Document not found");
        }

        return document.get();
    }

    public DocumentEntity saveDocument(Long loanId, MultipartFile file) throws IOException {
        Optional<LoanEntity> possibleLoan = loanRepository.findById(loanId);

        if (possibleLoan.isEmpty()) {
            throw new EntityNotFoundException("Loan not found");
        }

        LoanEntity loan = possibleLoan.get();

        if (!file.getContentType().equals("application/pdf")) {
            throw new BadRequestException("File is not an pdf");
        }

        DocumentEntity document = DocumentEntity.builder()
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .data(file.getBytes())
                .loan(loan)
                .build();

        return documentRepository.save(document);
    }

    public void deleteDocumentById(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new EntityNotFoundException("Document not found");
        }

        documentRepository.deleteById(id);
    }
}
