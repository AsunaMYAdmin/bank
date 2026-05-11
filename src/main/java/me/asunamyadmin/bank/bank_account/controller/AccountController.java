package me.asunamyadmin.bank.bank_account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.asunamyadmin.bank.bank_account.exception.AccountTypeAlreadyExistsException;
import me.asunamyadmin.bank.bank_account.exception.NotValidAccountException;
import me.asunamyadmin.bank.bank_account.service.AccountDTO;
import me.asunamyadmin.bank.bank_account.service.AccountService;
import me.asunamyadmin.bank.bank_account.service.AccountStatus;
import me.asunamyadmin.bank.bank_account.service.AccountType;
import me.asunamyadmin.bank.bank_profile.service.ProfileDTO;
import me.asunamyadmin.bank.bank_profile.service.ProfileService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;
    private final ProfileService profileService;

    @GetMapping()
    public String accountPage(Model model, @AuthenticationPrincipal OAuth2User principal) {
        String username = principal.getAttribute("sub");
        List<AccountDTO> accounts = accountService.getAllAccountsByUsername(username);

        BigDecimal totalCrystals = accountService.getCrystalBalanceFromUsername(username);

        model.addAttribute("totalCrystals", totalCrystals);
        model.addAttribute("accounts", accounts);
        return "bank-accounts";
    }

    @GetMapping("/open")
    public String openPage() {
        return "account-open";
    }

    @PostMapping("/open")
    public String openAccount(Model model, @AuthenticationPrincipal OAuth2User principal, RedirectAttributes redirectAttributes,
                            @RequestParam("accountType")  String accountType) {
        log.info("Principal: {}", principal);
        try {
            String username = principal.getAttribute("sub");
            ProfileDTO profile = profileService.getDTOByName(username);
            AccountDTO account = new AccountDTO(
                    profile.id(),
                    null,
                    BigDecimal.ZERO,
                    accountService.getCurrencyFromType(accountType),
                    AccountType.valueOf(accountType),
                    AccountStatus.ACTIVE,
                    false,
                    LocalDateTime.now()
            );
            if (accountService.accountIsExists(username, AccountType.valueOf(accountType))) {
                throw new AccountTypeAlreadyExistsException();
            }
            accountService.createAccount(account);
            redirectAttributes.addFlashAttribute("successMessage", "Счёт успешно открыт!");
            return "redirect:/accounts";
        }catch (AccountTypeAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/accounts/open";
        }
        catch (Exception e) {
            log.error("Ошибка при открытии счёта", e);
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Ошибка при открытии счёта!");
            return "redirect:/accounts/open";
        }
    }

    @GetMapping("/{accountNumber}")
    public String accountDetailsPage(Model model, @PathVariable String accountNumber, @AuthenticationPrincipal OAuth2User principal) {
        String username = principal.getAttribute("sub");
        if (accountService.isNotValidAccount(accountNumber, username)) {
            throw new NotValidAccountException();
        }
        AccountDTO account = accountService.getAccountByNumber(accountNumber);
        model.addAttribute("account", account);
        return "account-details";
    }

    @PostMapping("/delete/{accountNumber}")
    public String deleteAccount(@PathVariable String accountNumber, RedirectAttributes redirectAttributes, @AuthenticationPrincipal OAuth2User principal) {
        String username = principal.getAttribute("sub");
        if (accountService.isNotValidAccount(accountNumber, username)) {
            throw new NotValidAccountException();
        }
        try {
            accountService.deleteAccount(accountNumber);
            redirectAttributes.addFlashAttribute("successMessage", "Счёт успешно удалён");
            return "redirect:/accounts";
        } catch (Exception e) {
            log.error("Ошибка при удаления аккаунта - {}",  accountNumber, e);
            redirectAttributes.addFlashAttribute("Ошибка при удалении платежа.");
            return "redirect:/accounts/" + accountNumber;
        }
    }
}