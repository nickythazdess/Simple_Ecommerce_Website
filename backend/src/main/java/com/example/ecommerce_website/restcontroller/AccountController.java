package com.example.ecommerce_website.restcontroller;

import com.example.ecommerce_website.dto.AccountDTO;
import com.example.ecommerce_website.entity.Account;
import com.example.ecommerce_website.exception.account.AccountNotFoundException;
import com.example.ecommerce_website.exception.account.EmailNotFoundException;
import com.example.ecommerce_website.exception.account.UsernameNotFoundException;
import com.example.ecommerce_website.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<AccountDTO>> getAll(){
        return new ResponseEntity<>(accountService.convertToDtoList(accountService.getAccountList()), HttpStatus.OK);
    }

    @GetMapping("/{uid}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public AccountDTO getUser(@PathVariable Long uid){
        Optional<Account> acc = accountService.getAccount(uid);
        if (!acc.isPresent()) throw new AccountNotFoundException(uid);
        return accountService.convertToDto(acc.get());
    }

    @GetMapping("/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<AccountDTO> getUsersByName(@PathVariable String name){
        List<Account> acc = accountService.getUserByName(name);
        if (acc.isEmpty()) throw new AccountNotFoundException(name);
        return accountService.convertToDtoList(acc);
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public AccountDTO getUserByEmail(@PathVariable String email){
        Optional<Account> acc = accountService.getUserByEmail(email);
        if (!acc.isPresent()) throw new EmailNotFoundException(email);
        return accountService.convertToDto(acc.get());
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public AccountDTO getUserByUsername(@PathVariable String username){
        Optional<Account> acc = accountService.getUserByUsername(username);
        if (!acc.isPresent()) throw new UsernameNotFoundException(username);
        return accountService.convertToDto(acc.get());
    }
}