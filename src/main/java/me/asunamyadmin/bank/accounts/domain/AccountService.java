package me.asunamyadmin.bank.accounts.domain;

import me.asunamyadmin.bank.accounts.data.AccountEntity;
import me.asunamyadmin.bank.accounts.data.AccountRepository;
import me.asunamyadmin.bank.accounts.exception.AccountHasAlreadyBeenBlocked;
import me.asunamyadmin.bank.accounts.exception.AccountNotFoundException;
import me.asunamyadmin.bank.accounts.exception.SelfTransferException;
import me.asunamyadmin.bank.user.data.UserRepository;
import me.asunamyadmin.bank.user.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserRepository userRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.accountMapper = new AccountMapper();
        this.userRepository = userRepository;
    }

    public Account findById(int id) {
        AccountEntity entity = getEntityFromRepository(id);
        return accountMapper.toAccount(entity);
    }

    public List<Account> findAll() {
        List<AccountEntity> entities = new ArrayList<>(accountRepository.findAll());
        List<Account> accounts = new ArrayList<>();
        for (AccountEntity entity : entities) {
            accounts.add(accountMapper.toAccount(entity));
        }
        return accounts;
    }

    @Transactional
    public Account create(Account account) {
        AccountEntity entity = accountMapper.toEntity(account);
        entity.setUser(userRepository.findById(account.userId()).orElseThrow(UserNotFoundException::new));
        entity = accountRepository.save(entity);
        return accountMapper.toAccount(entity);
    }


    @Transactional
    public Account update(Account account) {
        AccountEntity entity = getEntityFromRepository(account.id());
        entity.setAccountNumber(account.account_number());
        return accountMapper.toAccount(entity);
    }

    @Transactional
    public void delete(int id) {
        AccountEntity entity = getEntityFromRepository(id);
        accountRepository.delete(entity);
    }

    @Transactional
    public void transferMoney(Transfer transfer, BigDecimal amount) {
        if (transfer.fromId() == transfer.toId()) {
            throw new SelfTransferException();
        }
            AccountEntity fromEntity = getEntityFromRepository(transfer.fromId());
            AccountEntity toEntity = getEntityFromRepository(transfer.toId());
            fromEntity.withdraw(amount);
            toEntity.deposit(amount);
    }

    @Transactional
    public String banAccount(int id) {
        AccountEntity entity = getEntityFromRepository(id);
        if (entity.isBlocked()) {
            throw new AccountHasAlreadyBeenBlocked();
        }
        entity.block();
        return "Account has been blocked!";
    }

    @Transactional
    public String unBanAccount(int id) {
        AccountEntity entity = getEntityFromRepository(id);
        if (entity.isBlocked()) {
            entity.unblock();
            return "Account has been unblocked!";
        }
        return "This account is not banned.";
    }

    private AccountEntity getEntityFromRepository(int id) {
        return accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
    }
}
