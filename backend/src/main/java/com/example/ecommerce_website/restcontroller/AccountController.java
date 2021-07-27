package com.example.ecommerce_website.restcontroller;

import com.example.ecommerce_website.dto.AccountDTO;
import com.example.ecommerce_website.entity.Account;
import com.example.ecommerce_website.exception.account.*;
import com.example.ecommerce_website.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping()
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

    @PutMapping("/admin")
    public ResponseEntity<?> editAccount(@RequestBody(required = true) AccountDTO accountUpdate) throws ParseException {
        Optional<Account> acc = accountService.getAccount(accountUpdate.getId());
        if (!acc.isPresent()) throw new AccountNotFoundException(accountUpdate.getId());
        Account account = acc.get();

        if (!accountUpdate.getName().isEmpty()) account.setName(accountUpdate.getName());
        if (!accountUpdate.getUsername().isEmpty()) account.setUsername(accountUpdate.getUsername());
        if (!accountUpdate.getEmail().isEmpty()) account.setEmail(accountUpdate.getEmail());
        if (!accountUpdate.getPassword().isEmpty()) account.setPassword(encoder.encode(accountUpdate.getPassword()));

        return ResponseEntity.ok().body(accountService.updateAccount(account));
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        Optional<Account> acc = accountService.getAccount(id);
        if (!acc.isPresent()) {
            throw new AccountNotFoundException(id);
        }
        accountService.deleteAccount(id);
        return ResponseEntity.ok().body("Delete successfully!");
    }
}
