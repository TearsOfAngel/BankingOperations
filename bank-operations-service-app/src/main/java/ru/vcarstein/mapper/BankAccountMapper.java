package ru.vcarstein.mapper;

import ru.vcarstein.dto.BankAccountDTO;
import org.mapstruct.Mapper;
import ru.vcarstein.entity.BankAccount;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {
    BankAccount fromDTOtoEntity(BankAccountDTO bankAccountDTO);
    BankAccountDTO fromEntityToDTO(BankAccount bankAccount);
}
