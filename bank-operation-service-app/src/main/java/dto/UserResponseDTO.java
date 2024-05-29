package dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserResponseDTO {
    private String username;
    private String fullName;
    private String phone;
    private String email;
    private LocalDate birthDate;
    private BankAccountDTO bankAccount;
}
