package me.asunamyadmin.bank.bank_account.service;

import me.asunamyadmin.bank.bank_account.data.AccountEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public AccountDTO getAccountFromEntity(AccountEntity entity) {
        return new AccountDTO(
                entity.getUserId(),
                entity.getAccountNumber(),
                entity.getBalance(),
                entity.getCurrency(),
                entity.getAccountType(),
                entity.getStatus(),
                entity.getIsBlocked(),
                entity.getCreatedAt()
        );
    }
}
