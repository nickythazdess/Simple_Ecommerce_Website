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

    @GetMapping("/public")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok().body(accountService.convertToDtoList(accountService.getAccountList()));
    }

    @GetMapping("/admin/{uid}")
    public ResponseEntity<?> getUser(@PathVariable Long uid){
        Optional<Account> acc = accountService.getAccount(uid);
        if (!acc.isPresent()) throw new AccountNotFoundException(uid);
        return ResponseEntity.ok().body(accountService.convertToDto(acc.get()));
    }

    @GetMapping("/admin/name/{name}")
    public ResponseEntity<?> getUsersByName(@PathVariable String name){
        List<Account> accList = accountService.getUserByName(name);
        if (accList.isEmpty()) throw new AccountNotFoundException(name);
        return ResponseEntity.ok().body(accountService.convertToDtoList(accList));
    }

    @GetMapping("/admin/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email){
        Optional<Account> acc = accountService.getUserByEmail(email);
        if (!acc.isPresent()) throw new EmailNotFoundException(email);
        return ResponseEntity.ok().body(accountService.convertToDto(acc.get()));
    }

    @GetMapping("/admin/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username){
        Optional<Account> acc = accountService.getUserByUsername(username);
        if (!acc.isPresent()) throw new UsernameNotFoundException(username);
        return ResponseEntity.ok().body(accountService.convertToDto(acc.get()));
    }
}
