package me.asunamyadmin.bank.bank_transaction.Controller;

import lombok.RequiredArgsConstructor;
import me.asunamyadmin.bank.bank_account.service.AccountDTO;
import me.asunamyadmin.bank.bank_account.service.AccountService;
import me.asunamyadmin.bank.bank_transaction.service.TransactionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class TransactionController {
    private final TransactionService service;
    private final AccountService accountService;

    @GetMapping
    public String transferPage (@RequestParam(required = false) String from, Model model, @AuthenticationPrincipal OAuth2User principal) {
        String username = principal.getAttribute("sub");
        List<AccountDTO> accounts = accountService.getAllAccountsByUsername(username);

        model.addAttribute("accounts", accounts);
        model.addAttribute("selectedAccount", from);
        return "transfer";
    }
}
