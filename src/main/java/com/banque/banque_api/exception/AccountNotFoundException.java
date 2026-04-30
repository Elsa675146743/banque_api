package com.banque.banque_api.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(Long id) {
        super("Compte non trouvé avec l'id : " + id);
    }
}