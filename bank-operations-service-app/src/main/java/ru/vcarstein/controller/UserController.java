package ru.vcarstein.controller;

import ru.vcarstein.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vcarstein.enums.ContactType;
import ru.vcarstein.dto.UpdateContactDTO;
import ru.vcarstein.service.UserService;

import java.security.Principal;

@RequestMapping("/api/users")
@Tag(name = "User's actions", description = "Change number or email, delete contact")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/contact")
    public ResponseEntity<UserDTO> removeUserContactDetail(
            @RequestParam ContactType contactType,
            Principal principal) {
        return ResponseEntity.ok(userService.removeUserContactDetail(principal.getName(), contactType));
    }

    @PutMapping("/contacts")
    @Operation(summary = "Update user contacts", description = "Allows users to update their phone and/or email.")
    public ResponseEntity<UserDTO> updateUserContacts(@RequestBody UpdateContactDTO updateContactDTO, Principal principal) {
        UserDTO updatedUser = userService.updateUserContacts(principal.getName(), updateContactDTO);
        return ResponseEntity.ok(updatedUser);
    }
}
