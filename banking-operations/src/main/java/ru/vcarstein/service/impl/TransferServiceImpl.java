package ru.vcarstein.service.impl;

import dto.BankAccountDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vcarstein.entity.BankAccount;
import ru.vcarstein.exception.TransferException;
import ru.vcarstein.mapper.BankAccountMapper;
import ru.vcarstein.repository.BankAccountRepository;
import ru.vcarstein.service.TransferService;

@Service
public class TransferServiceImpl implements TransferService {
    private final BankAccountRepository accountRepository;

    private final BankAccountMapper bankAccountMapper;

    public TransferServiceImpl(BankAccountRepository accountRepository, BankAccountMapper bankAccountMapper) {
        this.accountRepository = accountRepository;
        this.bankAccountMapper = bankAccountMapper;
    }

    @Transactional
    public void transferMoney(Long senderId, Long recipientId, Double amount) throws TransferException {
        BankAccount senderAccount = accountRepository.findById(senderId)
                .orElseThrow(() -> new TransferException("Sender account not found"));
        BankAccount recipientAccount = accountRepository.findById(recipientId)
                .orElseThrow(() -> new TransferException("Recipient account not found"));

        BankAccountDTO senderDTO = bankAccountMapper.fromEntityToDTO(senderAccount);
        BankAccountDTO recipientDTO = bankAccountMapper.fromEntityToDTO(recipientAccount);

        if (senderAccount.getBalance().compareTo(amount) < 0) {
            throw new TransferException("Insufficient funds");
        }

        senderAccount.setBalance(senderDTO.getBalance() - amount);
        recipientAccount.setBalance(recipientDTO.getBalance() + amount);

        accountRepository.save(senderAccount);
        accountRepository.save(recipientAccount);
    }
}
