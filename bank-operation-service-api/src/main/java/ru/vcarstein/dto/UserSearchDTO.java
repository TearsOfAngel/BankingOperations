package ru.vcarstein.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

@Data
@Schema(description = "DTO for searching users with pagination and filtering")
public class UserSearchDTO {

    @Schema(description = "Full name for searching users")
    private String fullName;

    @Schema(description = "Phone number for searching users")
    private String phone;

    @Schema(description = "Email for searching users")
    private String email;

    @Schema(description = "Birth date for searching users")
    private LocalDate birthDate;

    @Schema(description = "Page number for pagination", defaultValue = "0")
    private int page = 0;

    @Schema(description = "Page size for pagination", defaultValue = "10")
    private int size = 10;

    @Schema(description = "Sort direction for results", defaultValue = "ASC")
    private Sort.Direction sortDirection = Sort.Direction.ASC;

    @Schema(description = "Field to sort by", defaultValue = "id")
    private String sortBy = "id";
}