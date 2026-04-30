package com.banque.banque_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "Données pour créer un nouveau compte bancaire")
public class CreateAccountRequest {

    @NotBlank(message = "Le nom du titulaire est obligatoire")
    @Schema(example = "Marie Dupont")
    private String ownerName;

    @NotNull(message = "Le solde initial est obligatoire")
    @DecimalMin(value = "0.0", message = "Le solde initial ne peut pas être négatif")
    @Schema(example = "1000.00")
    private BigDecimal initialBalance;

    public CreateAccountRequest() {
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }
}