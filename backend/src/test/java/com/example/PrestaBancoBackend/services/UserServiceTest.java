package com.example.PrestaBancoBackend.services;

import com.example.PrestaBancoBackend.dtos.UserDTO;
import com.example.PrestaBancoBackend.entities.UserEntity;
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

import java.sql.Date;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenGetAllUsers_HaveOneUsers() {
        //Given
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setFirstName("Juan");

        //When
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1));

        List<UserEntity> users = userService.getAllUsers();

        //Then
        assertThat(users).hasSize(1);
        assertThat(users).contains(user1);
    }

    @Test
    void whenGetAllUsers_HaveTwoUsers() {
        //Given
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setFirstName("Juan");

        UserEntity user2 = new UserEntity();
        user2.setId(2L);
        user2.setFirstName("Carlos");

        //When
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<UserEntity> users = userService.getAllUsers();

        //Then
        assertThat(users).hasSize(2);
        assertThat(users).contains(user1, user2);
    }

    @Test
    void whenGetAllUsers_HaveThreeUsers() {
        //Given
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setFirstName("Juan");

        UserEntity user2 = new UserEntity();
        user2.setId(2L);
        user2.setFirstName("Carlos");

        UserEntity user3 = new UserEntity();
        user3.setId(3L);
        user3.setFirstName("Maria");

        //When
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2, user3));

        List<UserEntity> users = userService.getAllUsers();

        //Then
        assertThat(users).hasSize(3);
        assertThat(users).contains(user1, user2, user3);
    }

    @Test
    void whenGetAllUsers_HaveFourUsers() {
        //Given
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setFirstName("Juan");

        UserEntity user2 = new UserEntity();
        user2.setId(2L);
        user2.setFirstName("Carlos");

        UserEntity user3 = new UserEntity();
        user3.setId(3L);
        user3.setFirstName("Maria");

        UserEntity user4 = new UserEntity();
        user4.setId(4L);
        user4.setFirstName("Ignacio");

        //When
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2, user3, user4));

        List<UserEntity> users = userService.getAllUsers();

        //Then
        assertThat(users).hasSize(4);
        assertThat(users).contains(user1, user2, user3, user4);
    }

    @Test
    void whenGetAllUsers_DontHaveUsers() {
        //When
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        List<UserEntity> users = userService.getAllUsers();

        //Then
        assertThat(users).isEqualTo(new ArrayList<UserEntity>());
    }

    @Test
    void whenGetUserById_UserIsFound() {
        // Given
        Long userId = 1L;
        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setFirstName("Juan");

        // When
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        UserEntity foundUser = userService.getUserById(userId);

        // Then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(userId);
        assertThat(foundUser.getFirstName()).isEqualTo("Juan");
    }

    @Test
    void whenGetUserById_MultipleUsersExist() {
        // Given
        Long userId = 1L;
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setFirstName("Juan");

        UserEntity user2 = new UserEntity();
        user2.setId(2L);
        user2.setFirstName("Carlos");

        // When
        when(userRepository.findById(userId)).thenReturn(Optional.of(user1));
        UserEntity foundUser = userService.getUserById(userId);

        // Then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getFirstName()).isEqualTo("Juan");
    }

    @Test
    void whenGetUserById_UserIsNotFound() {
        // Given
        Long userId = 1L;

        //When
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //Then
        assertThatThrownBy(() -> userService.getUserById(userId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    void whenGetUserById_IdIsNull() {
        // When & Then
        assertThatThrownBy(() -> userService.getUserById(null))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    void whenGetUserById_IdIsNegative() {
        // Given
        Long invalidId = -1L;

        // When & Then
        assertThatThrownBy(() -> userService.getUserById(invalidId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    void whenGetUserByRut_UserIsFound() {
        // Given
        String rut = "11111111-K";
        UserEntity user = new UserEntity();
        user.setRut(rut);
        user.setFirstName("Juan");

        // When
        when(userRepository.findByRut(rut)).thenReturn(Optional.of(user));
        UserEntity foundUser = userService.getUserByRut(rut);

        // Then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getRut()).isEqualTo(rut);
        assertThat(foundUser.getFirstName()).isEqualTo("Juan");
    }

    @Test
    void whenGetUserByRut_UserIsNotFound() {
        // Given
        String rut = "11111111-K";

        // When & Then
        when(userRepository.findByRut(rut)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.getUserByRut(rut))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    void whenGetUserByRut_RutIsNull() {
        // When & Then
        assertThatThrownBy(() -> userService.getUserByRut(null))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    void whenGetUserByRut_RutIsInvalid() {
        // Given
        UserEntity user = new UserEntity();
        user.setRut("11111111-k");
        user.setFirstName("Juan");
        String invalidRut = "11.111.111-k";

        // When & Then
        assertThatThrownBy(() -> userService.getUserByRut(invalidRut))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    void whenGetUserByRut_DatabaseErrorOccurs() {
        // Given
        String rut = "12345678-9";

        // When & Then
        when(userRepository.findByRut(rut)).thenThrow(new RuntimeException("Database connection error"));
        assertThatThrownBy(() -> userService.getUserByRut(rut))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Database connection error");
    }

    @Test
    void whenCreateUser_UserIsCreated() {
        // Given
        UserDTO userDTO = new UserDTO(
                "12345678-9",
                "Juan",
                "Doe",
                Date.valueOf("1990-01-01"),
                "In validation"
        );

        when(userRepository.existsByRut(userDTO.getRut())).thenReturn(false);

        UserEntity savedUser = UserEntity.builder()
                .rut(userDTO.getRut())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .birthDate(userDTO.getBirthDate())
                .status(userDTO.getStatus())
                .build();

        // When
        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUser);
        UserEntity result = userService.createUser(userDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getRut()).isEqualTo(userDTO.getRut());
        assertThat(result.getFirstName()).isEqualTo("Juan");
    }

    @Test
    void whenCreateUser_RutAlreadyExists() {
        // Given
        UserDTO userDTO = new UserDTO(
                "12345678-9",
                "Juan",
                "Doe",
                Date.valueOf("1990-01-01"),
                "In validation"
        );

        // When & Then
        when(userRepository.existsByRut(userDTO.getRut())).thenReturn(true);
        assertThatThrownBy(() -> userService.createUser(userDTO))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("The rut is already used");
    }

    @Test
    void whenCreateUser_UserIsNull() {
        // Given
        UserDTO userDTO = null;

        // Then
        assertThatThrownBy(() -> userService.createUser(userDTO))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void whenCreateUser_RutIsNull() {
        // Given
        UserDTO userDTO = new UserDTO(
                null,
                "Juan",
                "Doe",
                Date.valueOf("1990-01-01"),
                "In validation"
        );

        // When
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

        // Then
        ConstraintViolation<UserDTO> violation = violations.stream()
                .filter(v -> "rut".equals(v.getPropertyPath().toString()))
                .findFirst()
                .orElse(null);

        assertNotNull(violation);
        assertEquals("Rut is required", violation.getMessage());
    }

    @Test
    void whenCreateUser_AllCampsAreNullOrEmpty() {
        // Given
        UserDTO userDTO = new UserDTO(
                "",
                "",
                "",
                null,
                ""
        );

        // When
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals(5, violations.size());
        for (ConstraintViolation<UserDTO> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();

            switch (propertyPath) {
                case "rut":
                    assertEquals("Rut is required", message);
                    break;
                case "firstName":
                    assertEquals("First name is required", message);
                    break;
                case "lastName":
                    assertEquals("Last name is required", message);
                    break;
                case "birthDate":
                    assertEquals("Birth date is required", message);
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
    void whenUpdateUser_UserIsUpdated() {
        // Given
        Long userId = 1L;
        UserDTO userDTO = new UserDTO(
                "12345678-9",
                "Juan",
                "Doe",
                Date.valueOf("1990-01-01"),
                "In validation"
        );

        // When
        UserEntity existingUser = new UserEntity();
        existingUser.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByRutAndIdNot(userDTO.getRut(), userId)).thenReturn(false);

        UserEntity updatedUser = UserEntity.builder()
                .id(userId)
                .rut(userDTO.getRut())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .birthDate(userDTO.getBirthDate())
                .status(userDTO.getStatus())
                .build();

        when(userRepository.save(any(UserEntity.class))).thenReturn(updatedUser);
        UserEntity result = userService.updateUser(userId, userDTO);

        // Then
        assertNotNull(result);
        assertEquals("Juan", result.getFirstName());
    }

    @Test
    void whenUpdateUser_NoChanges() {
        // Given
        Long userId = 1L;
        UserEntity existingUser = UserEntity.builder()
                .rut("12345678-9")
                .firstName("Juan")
                .lastName("Doe")
                .birthDate(Date.valueOf("1990-01-01"))
                .status("In validation")
                .build();

        // When
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByRutAndIdNot(existingUser.getRut(), userId)).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenReturn(existingUser);

        UserDTO userDTO = new UserDTO(
                existingUser.getRut(),
                existingUser.getFirstName(),
                existingUser.getLastName(),
                existingUser.getBirthDate(),
                existingUser.getStatus()
        );

        UserEntity result = userService.updateUser(userId, userDTO);

        // Then
        assertNotNull(result);
        assertEquals(existingUser.getRut(), result.getRut());
        assertEquals(existingUser.getFirstName(), result.getFirstName());
        assertEquals(existingUser.getLastName(), result.getLastName());
        assertEquals(existingUser.getBirthDate(), result.getBirthDate());
        assertEquals(existingUser.getStatus(), result.getStatus());
    }

    @Test
    void whenUpdateUser_UserNotFound() {
        // Given
        Long userId = 1L;
        UserDTO userDTO = new UserDTO(
                "12345678-9",
                "Juan",
                "Doe",
                Date.valueOf("1990-01-01"),
                "In validation"
        );

        // When
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> userService.updateUser(userId, userDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    void whenUpdateUser_RutAlreadyUsed() {
        // Given
        Long userId = 1L;
        UserDTO userDTO = new UserDTO(
                "12345678-9",
                "Juan",
                "Doe",
                Date.valueOf("1990-01-01"),
                "In validation"
        );

        // When
        UserEntity existingUser = new UserEntity();
        existingUser.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByRutAndIdNot(userDTO.getRut(), userId)).thenReturn(true);

        // Then
        assertThatThrownBy(() -> userService.updateUser(userId, userDTO))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("The rut is already used");
    }

    @Test
    void whenUpdateUser_AllCampsAreNullOrEmpty() {
        // Given
        Long userId = 1L;

        UserDTO userDTO = new UserDTO("", "", "", null, "");

        // When
        UserEntity existingUser = new UserEntity();
        existingUser.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals(5, violations.size());
        for (ConstraintViolation<UserDTO> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();

            switch (propertyPath) {
                case "rut":
                    assertEquals("Rut is required", message);
                    break;
                case "firstName":
                    assertEquals("First name is required", message);
                    break;
                case "lastName":
                    assertEquals("Last name is required", message);
                    break;
                case "birthDate":
                    assertEquals("Birth date is required", message);
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
    void whenDeleteUserById_UserDeleted() {
        // Given
        Long userId = 1L;

        // When
        when(userRepository.existsById(userId)).thenReturn(true);
        userService.deleteUserById(userId);

        // Then
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void whenDeleteUserById_UserNotFound() {
        // Given
        Long userId = 1L;

        // When
        when(userRepository.existsById(userId)).thenReturn(false);

        // Then
        assertThatThrownBy(() -> userService.deleteUserById(userId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    void whenDeleteUserById_IdIsNull() {
        // When & Then
        assertThatThrownBy(() -> userService.deleteUserById(null))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    void whenDeleteUserById_UserAlreadyDeleted() {
        // Given
        Long userId = 1L;

        // When
        when(userRepository.existsById(userId)).thenReturn(false);

        // Then
        assertThatThrownBy(() -> userService.deleteUserById(null))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    void whenDeleteUserById_UnexpectedErrorDuringDeletion() {
        // Given
        Long userId = 1L;

        // When
        when(userRepository.existsById(userId)).thenReturn(true);
        doThrow(new RuntimeException("Unexpected error")).when(userRepository).deleteById(userId);

        // Then
        assertThatThrownBy(() -> userService.deleteUserById(userId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Unexpected error");
    }
}
