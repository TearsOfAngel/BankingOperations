package ru.vcarstein.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bank_accounts")
@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double balance;

    @Column(nullable = false)
    private Double initialBalance;

    @PrePersist
    public void prePersist() {
        if (this.initialBalance == null) {
            this.initialBalance = this.balance;
        }
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }
}
