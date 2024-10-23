package com.example.PrestaBancoBackend.controllers;

import com.example.PrestaBancoBackend.entities.DocumentEntity;
import com.example.PrestaBancoBackend.services.DocumentService;
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
    public ResponseEntity<DocumentEntity> getDocument(@PathVariable Long id) {
        return new ResponseEntity<>(documentService.getDocumentById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DocumentEntity> postDocument(
            @RequestParam(name = "loan_id") Long loanId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        return new ResponseEntity<>(documentService.saveDocument(loanId, file), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocumentById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("error", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
