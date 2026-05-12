package me.asunamyadmin.bank.bank_credits.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/credits")
public class CreditsController {
    @GetMapping
    public String credits(){
        return "credits";
    }
}
