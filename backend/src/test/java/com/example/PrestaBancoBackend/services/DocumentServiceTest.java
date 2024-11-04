package com.example.PrestaBancoBackend.services;

import com.example.PrestaBancoBackend.entities.DocumentEntity;
import com.example.PrestaBancoBackend.entities.LoanEntity;
import com.example.PrestaBancoBackend.repositories.DocumentRepository;
import com.example.PrestaBancoBackend.repositories.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DocumentServiceTest {
    @Mock
    DocumentRepository documentRepository;

    @Mock
    LoanRepository loanRepository;

    @InjectMocks
    DocumentService documentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetAllDocuments_HaveOneDocument() {
        // Given
        DocumentEntity document1 = new DocumentEntity();

        // When
        when(documentRepository.findAll()).thenReturn(Arrays.asList(document1));
        List<DocumentEntity> documents = documentService.getAllDocuments();

        // Then
        assertThat(documents).hasSize(1);
        assertThat(documents).contains(document1);
    }

    @Test
    void whenGetAllDocuments_HaveTwoDocuments() {
        // Given
        DocumentEntity document1 = new DocumentEntity();
        DocumentEntity document2 = new DocumentEntity();

        // When
        when(documentRepository.findAll()).thenReturn(Arrays.asList(document1, document2));
        List<DocumentEntity> documents = documentService.getAllDocuments();

        // Then
        assertThat(documents).hasSize(2);
        assertThat(documents).contains(document1, document2);
    }

    @Test
    void whenGetAllDocuments_HaveThreeDocuments() {
        // Given
        DocumentEntity document1 = new DocumentEntity();
        DocumentEntity document2 = new DocumentEntity();
        DocumentEntity document3 = new DocumentEntity();

        // When
        when(documentRepository.findAll()).thenReturn(Arrays.asList(document1, document2, document3));
        List<DocumentEntity> documents = documentService.getAllDocuments();

        // Then
        assertThat(documents).hasSize(3);
        assertThat(documents).contains(document1, document2, document3);
    }

    @Test
    void whenGetAllDocuments_HaveFourDocuments() {
        // Given
        DocumentEntity document1 = new DocumentEntity();
        DocumentEntity document2 = new DocumentEntity();
        DocumentEntity document3 = new DocumentEntity();
        DocumentEntity document4 = new DocumentEntity();

        // When
        when(documentRepository.findAll()).thenReturn(Arrays.asList(document1, document2, document3, document4));
        List<DocumentEntity> documents = documentService.getAllDocuments();

        // Then
        assertThat(documents).hasSize(4);
        assertThat(documents).contains(document1, document2, document3, document4);
    }

    @Test
    void whenGetAllDocuments_DontHaveDocuments() {
        // When
        when(documentRepository.findAll()).thenReturn(new ArrayList<>());
        List<DocumentEntity> documents = documentService.getAllDocuments();

        // Then
        assertThat(documents).isEqualTo(new ArrayList<DocumentEntity>());
    }

    @Test
    void whenGetAllDocumentsByLoanId_LoanExistsWithDocuments() {
        // Given
        Long loanId = 1L;
        LoanEntity loan = new LoanEntity();
        loan.setId(loanId);

        DocumentEntity document1 = new DocumentEntity();
        document1.setFileName("document1.pdf");

        DocumentEntity document2 = new DocumentEntity();
        document2.setFileName("document2.pdf");

        // When
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
        when(documentRepository.findAllByLoanId(loanId)).thenReturn(Arrays.asList(document1, document2));
        List<DocumentEntity> documents = documentService.getAllDocumentsByLoanId(loanId);

        // Then
        assertThat(documents).isNotNull();
        assertThat(documents).hasSize(2);
        assertThat(documents.get(0)).isEqualTo(document1);
    }

    @Test
    void whenGetAllDocumentsByLoanId_LoanNotFound() {
        // Given
        Long loanId = 1L;

        // When
        when(loanRepository.findById(loanId)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> documentService.getAllDocumentsByLoanId(loanId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Loan not found");
    }

    @Test
    void whenGetAllDocumentsByLoanId_LoanExistsWithoutDocuments() {
        // Given
        Long loanId = 1L;
        LoanEntity loan = new LoanEntity();
        loan.setId(loanId);

        // When
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
        when(documentRepository.findAllByLoanId(loanId)).thenReturn(new ArrayList<>());
        List<DocumentEntity> documents = documentService.getAllDocumentsByLoanId(loanId);

        // Then
        assertThat(documents).isNotNull();
        assertThat(documents).isEqualTo(new ArrayList<DocumentEntity>());
    }

    @Test
    void whenGetAllDocumentsByLoanId_HaveThreeDocuments() {
        // Given
        Long loanId = 1L;

        LoanEntity loan = new LoanEntity();
        loan.setId(loanId);


        // When
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
        when(documentRepository.findAllByLoanId(loanId))
                .thenReturn(Arrays.asList(new DocumentEntity(), new DocumentEntity(), new DocumentEntity()));
        List<DocumentEntity> result = documentService.getAllDocumentsByLoanId(loanId);

        // Then
        assertThat(result).hasSize(3);
    }

    @Test
    void whenGetAllDocumentsByLoanId_NullLoanId() {
        // When
        when(documentRepository.findAllByLoanId(null)).thenReturn(new ArrayList<>());

        // Then
        assertThatThrownBy(() -> documentService.getAllDocumentsByLoanId(null))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Loan not found");
    }

    @Test
    void whenGetDocumentById_DocumentFount() {
        // Given
        Long id = 1L;
        DocumentEntity document1 = DocumentEntity.builder().id(id).build();

        // When
        when(documentRepository.findById(id)).thenReturn(Optional.of(document1));
        DocumentEntity foundDocument = documentService.getDocumentById(id);

        // Then
        assertThat(foundDocument).isNotNull();
        assertThat(foundDocument).isEqualTo(document1);
    }

    @Test
    void whenGetDocumentById_MultipleDocumentExists() {
        // Given
        Long id = 1L;
        DocumentEntity document1 = DocumentEntity.builder().id(id).build();
        DocumentEntity document2 = DocumentEntity.builder().id(2L).build();

        // When
        when(documentRepository.findById(id)).thenReturn(Optional.of(document1));
        DocumentEntity foundDocument = documentService.getDocumentById(id);

        // Then
        assertThat(foundDocument).isNotNull();
        assertThat(foundDocument).isEqualTo(document1);
        assertThat(foundDocument).isNotEqualTo(document2);
    }

    @Test
    void whenGetDocumentById_DocumentNotFound() {
        // Given
        Long id = 1L;

        // When
        when(documentRepository.findById(id)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> documentService.getDocumentById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Document not found");
    }

    @Test
    void whenGetDocumentById_IdIsNull() {
        // When
        when(documentRepository.findById(null)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> documentService.getDocumentById(null))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Document not found");
    }

    @Test
    void whenGetDocumentById_IdIsNegative() {
        // Given
        Long invalidId = -1L;

        // When
        when(documentRepository.findById(invalidId)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> documentService.getDocumentById(invalidId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Document not found");
    }

    @Test
    void whenSaveDocument_ValidLoanAndPdfFile() throws IOException {
        // Given
        Long loanId = 1L;
        MultipartFile file = new MockMultipartFile(
                "file",
                "document.pdf",
                "application/pdf",
                "Contenido del archivo".getBytes()
        );
        LoanEntity loan = new LoanEntity();
        loan.setId(loanId);

        // When
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
        when(documentRepository.save(any(DocumentEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        DocumentEntity savedDocument = documentService.saveDocument(loanId, file);

        // Then
        assertThat(savedDocument).isNotNull();
        assertThat(savedDocument.getFileName()).isEqualTo("document.pdf");
        assertThat(savedDocument.getFileType()).isEqualTo("application/pdf");
    }

    @Test
    void whenSaveDocument_LoanNotFound() {
        // Given
        Long loanId = 1L;
        MultipartFile file = new MockMultipartFile(
                "file",
                "document.pdf",
                "application/pdf",
                "Contenido del archivo".getBytes()
        );

        // When
        when(loanRepository.findById(loanId)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> documentService.saveDocument(loanId, file))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Loan not found");
    }

    @Test
    void whenSaveDocument_InvalidFileType() {
        // Given
        Long loanId = 1L;
        MultipartFile file = new MockMultipartFile(
                "file",
                "document.jpg",
                "application/jpg",
                "Contenido del archivo".getBytes()
        );

        LoanEntity loan = new LoanEntity();
        loan.setId(loanId);

        // When
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));

        // Then
        assertThatThrownBy(() -> documentService.saveDocument(loanId, file))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("File is not an pdf");
    }

    @Test
    void whenSaveDocument_LoanIdIsNull() {
        // Given
        MultipartFile file = new MockMultipartFile(
                "file",
                "document.pdf",
                "application/pdf",
                "Contenido del archivo".getBytes()
        );

        // When
        when(loanRepository.findById(null)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> documentService.saveDocument(null, file))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Loan not found");
    }

    @Test
    void whenSaveDocument_LoanIdIsNegative() {
        // Given
        Long invalidLoanId = -1L;
        MultipartFile file = new MockMultipartFile(
                "file",
                "document.pdf",
                "application/pdf",
                "Contenido del archivo".getBytes()
        );

        // When
        when(loanRepository.findById(invalidLoanId)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> documentService.saveDocument(invalidLoanId, file))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Loan not found");
    }

    @Test
    void whenDeleteDocumentById_DocumentDeleted() {
        // Given
        Long id = 1L;

        // When
        when(documentRepository.existsById(id)).thenReturn(true);
        documentService.deleteDocumentById(id);

        // Then
        verify(documentRepository, times(1)).deleteById(id);
    }

    @Test
    void whenDeleteDocumentById_DocumentNotFound() {
        // Given
        Long id = 1L;

        // When
        when(documentRepository.existsById(id)).thenReturn(false);

        // Then
        assertThatThrownBy(() -> documentService.deleteDocumentById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Document not found");
    }

    @Test
    void whenDeleteDocumentById_IdIsNull() {
        // When
        when(documentRepository.existsById(null)).thenReturn(false);

        // Then
        assertThatThrownBy(() -> documentService.deleteDocumentById(null))
            .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("Document not found");
    }

    @Test
    void whenDeleteDocumentById_DocumentAlreadyDeleted() {
        // Given
        Long id = 1L;

        // When
        when(documentRepository.existsById(id)).thenReturn(true);
        documentService.deleteDocumentById(id);
        when(documentRepository.existsById(id)).thenReturn(false);

        // Then
        assertThatThrownBy(() -> documentService.deleteDocumentById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Document not found");
    }

    @Test
    void whenDeleteDocumentById_unexpectedErrorDuringDeletion() {
        // Given
        Long id = 1L;

        // When
        when(documentRepository.existsById(id)).thenReturn(true);
        doThrow(new RuntimeException("Unexpected error")).when(documentRepository).deleteById(id);

        // Then
        assertThatThrownBy(() -> documentService.deleteDocumentById(id))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Unexpected error");

    }
}
