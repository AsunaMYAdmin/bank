package me.asunamyadmin.bank.bank_account.API.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.asunamyadmin.bank.bank_account.API.domain.CheckRequest;
import me.asunamyadmin.bank.bank_account.service.AccountDTO;
import me.asunamyadmin.bank.bank_account.service.AccountService;
import me.asunamyadmin.bank.bank_profile.service.ProfileDTO;
import me.asunamyadmin.bank.bank_profile.service.ProfileService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountRestController {
    private final AccountService accountService;
    private final ProfileService profileService;

    @PostMapping("/check")
    public Map<String, Object> checkAccount(@RequestBody CheckRequest checkRequest,
                                            @AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> response = new HashMap<>();
        String currentUsername = principal.getAttribute("sub");

        try {
            ProfileDTO profile = profileService.getDTOByName(checkRequest.username());
            if (profile == null) {
                response.put("found", false);
                response.put("message", "Игрок не найден.");
                return response;
            }

            AccountDTO account = accountService.getAccountByNumber(checkRequest.accountNumber());
            if (account == null) {
                response.put("found", false);
                response.put("message", "Счёт не найден.");
                return response;
            }

            if (currentUsername == null) {
                response.put("found", false);
                response.put("message", "Пользователь не аутентифицирован");
                return response;
            }

            if (currentUsername.equals(profile.username())) {
                response.put("found", false);
                response.put("message", "Нельзя перевести самому себе.");
                return response;
            }

            if (!account.userId().equals(profile.id())) {
                response.put("found", false);
                response.put("message", "Счёт не принадлежит указанному игроку.");
                return response;
            }

            response.put("found", true);
            response.put("name", profile.username());
            response.put("accountNumber", account.accountNumber());
            response.put("currency", account.currency().name());

        } catch (Exception e) {
            log.error("Ошибка при проверкe - {}", e.getMessage());
            response.put("found", false);
            response.put("message", "Ошибка при проверке.");
        }

        return response;
    }
}
