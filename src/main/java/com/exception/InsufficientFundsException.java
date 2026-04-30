package com.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(BigDecimal balance, BigDecimal amount) {
        super("Solde insuffisant. Disponible : " + balance + " | Demandé : " + amount);
    }
}