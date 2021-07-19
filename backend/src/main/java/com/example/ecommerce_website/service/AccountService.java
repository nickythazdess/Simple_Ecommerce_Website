package com.example.ecommerce_website.service;

import com.example.ecommerce_website.dto.AccountDTO;
import com.example.ecommerce_website.entity.Account;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public interface AccountService {
    List<Account> getAccountList();

    Optional<Account> getAccount(Long uid);

    List<Account> getUserByName(String name);

    Optional<Account> getUserByUsername(String username);

    Optional<Account> getUserByEmail(String email);

    AccountDTO convertToDto(Account acc);

    List<AccountDTO> convertToDtoList(List<Account> accList);

    Account convertToEntity(AccountDTO accountDTO) throws ParseException;
}
