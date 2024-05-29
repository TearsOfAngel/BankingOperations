package dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String fullName;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private BankAccountDTO bankAccount;
}
