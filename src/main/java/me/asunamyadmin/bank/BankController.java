package me.asunamyadmin.bank;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BankController {

    @GetMapping("/")
    public String index(){
        return "index";
    }
}
