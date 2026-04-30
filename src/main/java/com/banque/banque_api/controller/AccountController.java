package com.banque.banque_api.controller;

import com.banque.banque_api.dto.AccountResponse;
import com.banque.banque_api.dto.CreateAccountRequest;
import com.banque.banque_api.dto.TransactionRequest;
import com.banque.banque_api.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
@Tag(name = "Comptes Bancaires", description = "Gestion des comptes et transactions")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @Operation(summary = "Créer un compte bancaire")
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(request));
    }

    @GetMapping
    @Operation(summary = "Lister tous les comptes")
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un compte par ID")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PostMapping("/{id}/deposit")
    @Operation(summary = "Effectuer un dépôt")
    public ResponseEntity<AccountResponse> deposit(@PathVariable Long id,
            @Valid @RequestBody TransactionRequest request) {
        return ResponseEntity.ok(accountService.deposit(id, request.getAmount()));
    }

    @PostMapping("/{id}/withdraw")
    @Operation(summary = "Effectuer un retrait")
    public ResponseEntity<AccountResponse> withdraw(@PathVariable Long id,
            @Valid @RequestBody TransactionRequest request) {
        return ResponseEntity.ok(accountService.withdraw(id, request.getAmount()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un compte")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}