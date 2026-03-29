package me.asunamyadmin.bank.bank_transaction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.asunamyadmin.bank.bank_account.service.AccountService;
import me.asunamyadmin.bank.bank_account.service.Currency;
import me.asunamyadmin.bank.bank_transaction.data.TransactionEntity;
import me.asunamyadmin.bank.bank_transaction.data.TransactionRepository;
import me.asunamyadmin.bank.bank_transaction.exception.TransactionNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final AccountService accountService;
    private final TransactionMapper mapper;

    public List<TransactionDTO> getAllTransactionsFromId(Integer id) {
        return repository.findAllByFromAccountId(id).stream()
                .map(mapper::getTransactionFromEntity)
                .toList();
    }

    public List<TransactionDTO> getAllTransactionsToId(Integer id) {
        return repository.findAllByToAccountId(id).stream()
                .map(mapper::getTransactionFromEntity)
                .toList();
    }

    @Transactional
    public void transfer(Integer fromId, Integer toId, BigDecimal amount, String currency) {
        start(fromId, toId, amount, currency, TransactionType.TRANSFER);
    }

    @Transactional
    public void exchange(Integer fromId, Integer toId, BigDecimal amount, String currency) {
        start(fromId, toId, amount, currency, TransactionType.EXCHANGE);
    }

    @Transactional
    public void cancelTransfer(Long id) {
        TransactionEntity entity = repository.findById(id)
                .orElseThrow(TransactionNotFoundException::new);
        accountService.replenishment(entity.getFromAccountId(), entity.getAmount());
        accountService.withDraw(entity.getToAccountId(), entity.getAmount());
        entity.setStatus(TransactionStatus.FAILED);
        repository.save(entity);
    }

    private void start(Integer fromId, Integer toId, BigDecimal amount, String currency, TransactionType type) {
        TransactionEntity entity = new TransactionEntity();
        accountService.withDraw(fromId, amount);
        accountService.replenishment(toId, amount);
        entity.setFromAccountId(fromId);
        entity.setToAccountId(toId);
        entity.setAmount(amount);
        entity.setCurrency(Currency.valueOf(currency));
        entity.setType(type);
        entity.setStatus(TransactionStatus.DONE);
        entity.setProcessedAt(LocalDateTime.now());
        repository.save(entity);
    }
}
