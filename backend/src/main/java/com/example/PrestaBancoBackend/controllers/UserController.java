package com.example.PrestaBancoBackend.controllers;

import com.example.PrestaBancoBackend.dtos.UserDTO;
import com.example.PrestaBancoBackend.entities.UserEntity;
import com.example.PrestaBancoBackend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<UserEntity> getUserByRut(@PathVariable String rut) {
        return new ResponseEntity<>(userService.getUserByRut(rut), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserEntity> postUser(@Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> putUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.updateUser(id, userDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("error", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
