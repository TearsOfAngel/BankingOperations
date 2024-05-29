package ru.vcarstein.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vcarstein.entity.BankAccount;
import ru.vcarstein.repository.BankAccountRepository;
import ru.vcarstein.service.BalanceUpdaterService;

import java.util.List;

@Service
public class BalanceUpdaterImpl implements BalanceUpdaterService {

    private final BankAccountRepository bankAccountRepository;

    public BalanceUpdaterImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    @Override
    public void updateBalances() {
        List<BankAccount> accounts = bankAccountRepository.findAll();

        for (BankAccount account : accounts) {
            double initialBalance = account.getInitialBalance();
            double currentBalance = account.getBalance();
            double updatedBalance = currentBalance * 1.05;

            double maxAllowedBalance = initialBalance * 2.07;
            if (updatedBalance > maxAllowedBalance) {
                updatedBalance = maxAllowedBalance;
            }

            account.setBalance(updatedBalance);
        }
    }
}
