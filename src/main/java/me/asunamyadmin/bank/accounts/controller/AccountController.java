package me.asunamyadmin.bank.accounts.controller;

import me.asunamyadmin.bank.accounts.domain.Account;
import me.asunamyadmin.bank.accounts.domain.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    AccountService accountService;
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<Account>> getAllAccounts(){
        List<Account> accounts = new ArrayList<>(accountService.findAll());
        return ResponseEntity.ok().body(accounts);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountByID(@PathVariable int id){
        return ResponseEntity.ok().body(accountService.findById(id));
    }
    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody Account account){
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.create(account));
    }
    @PutMapping("/update")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account){
        return ResponseEntity.ok().body(accountService.update(account));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Account> deleteAccount(@PathVariable int id){
        accountService.delete(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/transfer")
    public ResponseEntity<Void> transferAccount(@RequestParam int from, @RequestParam int to, @RequestParam BigDecimal amount) {
        accountService.transferMoney(from, to, amount);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/ban/{id}")
    public ResponseEntity<BanDTO> ban(@PathVariable int id){
        String message = accountService.banAccount(id);
        return ResponseEntity.ok().body(new BanDTO(
                id,
                message
        ));
    }
    @PatchMapping("/unban/{id}")
    public ResponseEntity<BanDTO> unBanAccount(@PathVariable int id){
        String message = accountService.unBanAccount(id);
        return ResponseEntity.ok().body(new BanDTO(
                id,
                message
        ));
    }
}
