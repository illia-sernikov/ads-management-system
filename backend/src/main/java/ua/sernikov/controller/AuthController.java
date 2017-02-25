package ua.sernikov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.sernikov.domain.Account;
import ua.sernikov.service.AccountService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private AccountService accountService;

    @Autowired
    public AuthController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signin")
    public String signIn(@RequestBody @Valid Account account) {
        Assert.notNull(account);
        return accountService.signIn(account);
    }
}
