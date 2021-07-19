package com.example.ecommerce_website.service.impl;

import com.example.ecommerce_website.dto.AccountDTO;
import com.example.ecommerce_website.dto.CategoryDTO;
import com.example.ecommerce_website.entity.Account;
import com.example.ecommerce_website.entity.Category;
import com.example.ecommerce_website.repository.AccountRepository;
import com.example.ecommerce_website.repository.CategoryRepository;
import com.example.ecommerce_website.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository repo;
    @Autowired
    private ModelMapper modelMapper;

    public void setAccountRepo(AccountRepository repo) { this.repo = repo; }

    public List<Account> getAccountList() { return repo.findAll(); }

    public Optional<Account> getAccount(Long uid) { return repo.findById(uid); }

    public List<Account> getUserByName(String name) { return repo.findAllByName(name); }

    public Optional<Account> getUserByUsername(String username) { return repo.findByUsername(username); }

    public Optional<Account> getUserByEmail(String email) { return repo.findByEmail(email); }

    public AccountDTO convertToDto(Account acc){
        AccountDTO accountDTO = modelMapper.map(acc, AccountDTO.class);
        return accountDTO;
    }

    public List<AccountDTO> convertToDtoList(List<Account> accList){
        List<AccountDTO> dtoList = new ArrayList<>();
        for (Account acc : accList) {
            dtoList.add(convertToDto(acc));
        }
        return dtoList;
    }

    public Account convertToEntity(AccountDTO accountDTO) throws ParseException {
        Account acc = modelMapper.map(accountDTO, Account.class);
        return acc;
    }
}
