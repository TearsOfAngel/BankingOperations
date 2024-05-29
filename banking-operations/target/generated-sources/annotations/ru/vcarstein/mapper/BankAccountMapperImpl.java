package ru.vcarstein.mapper;

import dto.BankAccountDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.vcarstein.entity.BankAccount;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-29T06:25:30+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 22 (Oracle Corporation)"
)
@Component
public class BankAccountMapperImpl implements BankAccountMapper {

    @Override
    public BankAccount fromDTOtoEntity(BankAccountDTO bankAccountDTO) {
        if ( bankAccountDTO == null ) {
            return null;
        }

        BankAccount bankAccount = new BankAccount();

        bankAccount.setBalance( bankAccountDTO.getBalance() );

        return bankAccount;
    }

    @Override
    public BankAccountDTO fromEntityToDTO(BankAccount bankAccount) {
        if ( bankAccount == null ) {
            return null;
        }

        BankAccountDTO bankAccountDTO = new BankAccountDTO();

        bankAccountDTO.setBalance( bankAccount.getBalance() );

        return bankAccountDTO;
    }
}
