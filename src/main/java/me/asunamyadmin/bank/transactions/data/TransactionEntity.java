package me.asunamyadmin.bank.transactions.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import me.asunamyadmin.bank.accounts.data.AccountEntity;
import me.asunamyadmin.bank.transactions.domain.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account", nullable = false)
    @Setter
    AccountEntity fromAccount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account", nullable = false)
    @Setter
    AccountEntity toAccount;
    @Setter
    BigDecimal amount;
    @Enumerated(EnumType.STRING)
    @Column(insertable = false)
    TransactionStatus status;
    LocalDateTime createdAt;

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public TransactionEntity() {}

    public void markSuccessful() {
        this.status = TransactionStatus.SUCCESS;
    }
    public void markFailed() {
        this.status = TransactionStatus.FAILED;
    }
}
