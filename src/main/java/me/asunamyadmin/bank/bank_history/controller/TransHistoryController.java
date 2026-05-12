package me.asunamyadmin.bank.bank_history.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TransHistoryController {
    @GetMapping("/transactions")
    public String getTransactionsPage() {
        return "bank-transactions";
    }
}
