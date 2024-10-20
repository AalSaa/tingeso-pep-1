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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> getDocument(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(documentService.getDocumentById(id), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> postDocument(
            @RequestParam(name = "loan_id") Long loanId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            return new ResponseEntity<>(documentService.saveDocument(loanId, file), HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable Long id) {
        try {
            documentService.deleteDocumentById(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("error", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
}
