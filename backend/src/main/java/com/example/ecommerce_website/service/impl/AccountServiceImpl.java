package com.example.ecommerce_website.service.impl;

import com.example.ecommerce_website.displayDTO.AccountDisplay;
import com.example.ecommerce_website.dto.AccountDTO;
import com.example.ecommerce_website.entity.Account;
import com.example.ecommerce_website.repository.AccountRepository;
import com.example.ecommerce_website.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

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

    public Boolean existsByUsername(String username) {return repo.existsByUsername(username);}

    public Boolean existsByEmail(String email){return repo.existsByEmail(email);}

    public void deleteAccount(Long id) { repo.deleteById(id); }

    public Account updateAccount(Account acc) { return repo.save(acc); }

    public AccountDTO convertToDto(Account acc){
        AccountDTO accountDTO = modelMapper.map(acc, AccountDTO.class);
        Set<String> roles = new HashSet<>();
        acc.getRoles().forEach(role -> {
            roles.add(role.getName().name());
        });
        accountDTO.setRoles(roles);
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

    public AccountDisplay convertToDisplay(AccountDTO dto){
        AccountDisplay accountDisplay = modelMapper.map(dto, AccountDisplay.class);
        return accountDisplay;
    }

    public List<AccountDisplay> convertToDisplayList(List<AccountDTO> dtoList){
        List<AccountDisplay> displayList = new ArrayList<>();
        for (AccountDTO dto : dtoList) {
            displayList.add(convertToDisplay(dto));
        }
        return displayList;
    }
}
