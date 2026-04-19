package com.banque.banque_api.service;

import com.banque.banque_api.entity.Compte;
import com.banque.banque_api.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CompteService {

    @Autowired
    private CompteRepository compteRepository;

    // Créer un compte
    public Compte creerCompte(Compte compte) {
        return compteRepository.save(compte);
    }

    // Lister tous les comptes
    public List<Compte> listerTousLesComptes() {
        return compteRepository.findAll();
    }

    // Trouver un compte par numéro
    public Optional<Compte> trouverParNumero(String numeroCompte) {
        return compteRepository.findByNumeroCompte(numeroCompte);
    }

    // Déposer de l'argent
    public Optional<Compte> deposer(String numeroCompte, Double montant) {
        Optional<Compte> compteOpt = compteRepository.findByNumeroCompte(numeroCompte);
        if (compteOpt.isPresent() && montant > 0) {
            Compte compte = compteOpt.get();
            compte.setSolde(compte.getSolde() + montant);
            return Optional.of(compteRepository.save(compte));
        }
        return Optional.empty();
    }

    // Retirer de l'argent
    public Optional<Compte> retirer(String numeroCompte, Double montant) {
        Optional<Compte> compteOpt = compteRepository.findByNumeroCompte(numeroCompte);
        if (compteOpt.isPresent() && montant > 0 && compteOpt.get().getSolde() >= montant) {
            Compte compte = compteOpt.get();
            compte.setSolde(compte.getSolde() - montant);
            return Optional.of(compteRepository.save(compte));
        }
        return Optional.empty();
    }
}