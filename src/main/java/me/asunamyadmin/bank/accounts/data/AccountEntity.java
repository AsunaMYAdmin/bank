package me.asunamyadmin.bank.accounts.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import me.asunamyadmin.bank.accounts.exception.AccountBlockedException;
import me.asunamyadmin.bank.accounts.exception.InsufficientFundsException;
import me.asunamyadmin.bank.user.data.UserEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "accounts")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Setter
    private Integer accountNumber;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Setter
    private UserEntity user;
    @Version
    private Integer version;
    private BigDecimal balance;
    LocalDateTime created_at;
    private boolean isBlocked;

    public void withdraw(BigDecimal amount) {
        if (isBlocked) {
            throw new AccountBlockedException();
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InsufficientFundsException();
        }
        if (amount.compareTo(balance) > 0) {
            throw new InsufficientFundsException();
        }
        balance = balance.subtract(amount);
    }

    public void deposit(BigDecimal amount) {
        if (isBlocked) {
            throw new AccountBlockedException();
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InsufficientFundsException();
        }
        balance = balance.add(amount);
    }

    public void block () {
        isBlocked = true;
    }
    public void unblock() {
        isBlocked = false;
    }
}
