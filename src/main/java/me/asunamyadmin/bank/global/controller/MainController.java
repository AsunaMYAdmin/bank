package me.asunamyadmin.bank.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping
    public String mainPage() {
        return "index";
    }

    @GetMapping("/bank-about")
    public String aboutPage() {
        return "bank-about";
    }

    @GetMapping("/accounts")
    public String accountsPage() {
        return "bank-accounts";
    }

    @GetMapping("/faq")
    public String faqPage() {
        return "redirect:https://loregard.ru/faq";
    }

    @GetMapping("/help")
    public String helpPage() {
        return "redirect:https://loregard.ru/support";
    }

    @GetMapping("/contacts")
    public String contactsPage() {
        return "redirect:https://loregard.ru/contacts";
    }

    @GetMapping("/transactions")
    public String transactionsPage() {
        return "bank-transactions";
    }

    @GetMapping("/exchange")
    public String exchangePage() {
        return "bank-exchange";
    }

    @GetMapping("/vaults")
    public String vaultsPage() {
        return "bank-vaults";
    }

    @GetMapping("/site")
    public String sitePage() {
        return "redirect:https://loregard.ru";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "bank-login";
    }

    @GetMapping("/accounts/open")
    public String accountsOpenPage() {
        return "account-open";
    }

    @GetMapping("/transfer")
    public String transferPage() {
        return "transfer";
    }

    @GetMapping("/payment")
    public String paymentPage() {
        return "payment";
    }
}
