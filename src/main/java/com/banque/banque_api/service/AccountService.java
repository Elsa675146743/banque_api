package com.banque.banque_api.service;

import com.banque.banque_api.dto.AccountResponse;
import com.banque.banque_api.dto.CreateAccountRequest;
import com.banque.banque_api.entity.Account;
import com.banque.banque_api.exception.AccountNotFoundException;
import com.banque.banque_api.exception.InsufficientFundsException;
import com.banque.banque_api.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountResponse createAccount(CreateAccountRequest request) {
        Account account = new Account(request.getOwnerName(), request.getInitialBalance());
        return AccountResponse.fromEntity(accountRepository.save(account));
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(AccountResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AccountResponse getAccountById(Long id) {
        return AccountResponse.fromEntity(findOrThrow(id));
    }

    public AccountResponse deposit(Long id, BigDecimal amount) {
        Account account = findOrThrow(id);
        account.setBalance(account.getBalance().add(amount));
        return AccountResponse.fromEntity(accountRepository.save(account));
    }

    public AccountResponse withdraw(Long id, BigDecimal amount) {
        Account account = findOrThrow(id);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(account.getBalance(), amount);
        }
        account.setBalance(account.getBalance().subtract(amount));
        return AccountResponse.fromEntity(accountRepository.save(account));
    }

    public void deleteAccount(Long id) {
        accountRepository.delete(findOrThrow(id));
    }

    private Account findOrThrow(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }
}