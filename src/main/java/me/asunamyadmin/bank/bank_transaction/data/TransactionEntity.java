package me.asunamyadmin.bank.bank_transaction.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import me.asunamyadmin.bank.bank_account.service.Currency;
import me.asunamyadmin.bank.bank_transaction.service.TransactionStatus;
import me.asunamyadmin.bank.bank_transaction.service.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "from_account_id")
    Integer fromAccountId;
    @Column(name = "to_account_id")
    Integer toAccountId;
    BigDecimal amount;
    @Enumerated(value = EnumType.STRING)
    Currency currency;
    @Enumerated(value = EnumType.STRING)
    TransactionType type;
    @Enumerated(value = EnumType.STRING)
    TransactionStatus status;
    @Column(name = "created_at")
    LocalDateTime createdAt;
    @Column(name = "processed_at")
    LocalDateTime processedAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
