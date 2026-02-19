package me.asunamyadmin.bank.accounts.domain;

import me.asunamyadmin.bank.accounts.data.AccountEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public Account toAccount(AccountEntity accountEntity) {
        return new Account(
                accountEntity.getId(),
                accountEntity.getUser().getId(),
                accountEntity.getAccount_number(),
                accountEntity.getBalance(),
                accountEntity.getVersion(),
                accountEntity.getCreated_at()
        );
    }

    public AccountEntity toEntity(Account account) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccount_number(account.account_number());
        return accountEntity;
    }
}
