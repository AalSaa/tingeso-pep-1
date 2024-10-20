package com.example.PrestaBancoBackend.controllers;

import com.example.PrestaBancoBackend.entities.DocumentEntity;
import com.example.PrestaBancoBackend.services.DocumentService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/documents")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @GetMapping
    public ResponseEntity<List<DocumentEntity>> getDocuments() {
        return new ResponseEntity<>(documentService.getAllDocuments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentEntity> getDocument(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(documentService.getDocumentById(id), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<DocumentEntity> postDocument(
            @RequestParam(name = "loan_id") Long loanId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            return new ResponseEntity<>(documentService.saveDocument(loanId, file), HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DocumentEntity> deleteDocument(@PathVariable Long id) {
        try {
            documentService.deleteDocumentById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
