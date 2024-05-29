package ru.vcarstein.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vcarstein.entity.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
