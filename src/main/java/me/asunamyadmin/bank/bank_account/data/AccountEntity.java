package me.asunamyadmin.bank.bank_account.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import me.asunamyadmin.bank.bank_account.service.AccountStatus;
import me.asunamyadmin.bank.bank_account.service.AccountType;
import me.asunamyadmin.bank.bank_account.service.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "accounts")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "user_id")
    Integer userId;
    @Column(name = "account_number")
    String accountNumber;
    BigDecimal balance;
    @Enumerated(value = EnumType.STRING)
    Currency currency;
    @Column(name = "account_type")
    @Enumerated(value = EnumType.STRING)
    AccountType accountType;
    @Enumerated(value = EnumType.STRING)
    AccountStatus status;
    @Column(name = "created_At")
    LocalDateTime createdAt;
    @Version
    Long version;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
