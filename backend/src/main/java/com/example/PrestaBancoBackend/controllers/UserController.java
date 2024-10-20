package com.example.PrestaBancoBackend.controllers;

import com.example.PrestaBancoBackend.entities.UserEntity;
import com.example.PrestaBancoBackend.services.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserEntity>> getUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUser(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<UserEntity> postUser(@RequestBody UserEntity user) {
        try {
            return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserEntity> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
