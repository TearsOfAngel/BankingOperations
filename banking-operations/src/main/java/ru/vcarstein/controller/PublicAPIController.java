package ru.vcarstein.controller;

import dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dto.UserResponseDTO;
import ru.vcarstein.service.UserService;

import java.util.List;

@RequestMapping("/api/public")
@Tag(name = "Public API", description = "Adding or removing users")
@RestController
public class PublicAPIController {

    private final UserService userService;

    PublicAPIController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all users", description = "Allows you to get a list of all users")
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.findAll();
    }

    @Operation(summary = "Get user by id", description = "Allows you to get a user by his id")
    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Create user", description = "Allows you to create a user")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user) {
        return userService.createUser(user);
    }

    @Operation(summary = "Create bunch of users", description = "You can create a lot of users by one request")
    @PostMapping("/addList")
    public ResponseEntity<Void> createUsers(@RequestBody List<UserDTO> users) {
        return userService.createUsers(users);
    }

    @Operation(summary = "Delete user", description = "Allows you to delete user by his id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
