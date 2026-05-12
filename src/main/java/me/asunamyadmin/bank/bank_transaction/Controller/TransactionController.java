package me.asunamyadmin.bank.bank_transaction.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class TransactionController {
    @GetMapping("/transfer")
    public String transferPage (@RequestParam(required = false) String accountNumber, Model model) {
        return "transfer";
    }
}
