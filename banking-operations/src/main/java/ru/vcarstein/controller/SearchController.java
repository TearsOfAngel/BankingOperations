package ru.vcarstein.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dto.UserResponseDTO;
import ru.vcarstein.dto.UserSearchDTO;
import ru.vcarstein.service.UserSearchService;

import java.util.List;

@RequestMapping("/api/users")
@Tag(name = "Search users", description = "Pagination and filtration")
@RestController
public class SearchController {

    private final UserSearchService userSearchService;


    public SearchController(UserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }

    @Operation(summary = "Search users with pagination and filters",
            description = "Provides a list of users filtered by various parameters with pagination support.")
    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDTO>> searchUsers(UserSearchDTO searchDTO) {
        List<UserResponseDTO> result = userSearchService.searchUsers(searchDTO);
        return ResponseEntity.ok(result);
    }
}
