package com.banque.banque_api.controller;

import com.banque.banque_api.entity.Compte;
import com.banque.banque_api.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comptes")
public class CompteController {

    @Autowired
    private CompteService compteService;

    // GET /api/comptes - Lister tous les comptes
    @GetMapping
    public List<Compte> listerComptes() {
        return compteService.listerTousLesComptes();
    }

    // POST /api/comptes - Créer un compte
    @PostMapping
    public ResponseEntity<?> creerCompte(@RequestBody Compte compte) {
        try {
            Compte nouveauCompte = compteService.creerCompte(compte);
            return new ResponseEntity<>(nouveauCompte, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // GET /api/comptes/{numero} - Consulter un compte
    @GetMapping("/{numero}")
    public ResponseEntity<?> getCompte(@PathVariable String numero) {
        return compteService.trouverParNumero(numero)
                .map(compte -> ResponseEntity.ok(compte))
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/comptes/{numero}/depot - Faire un dépôt
    @PostMapping("/{numero}/depot")
    public ResponseEntity<?> deposer(@PathVariable String numero, @RequestBody Map<String, Double> body) {
        Double montant = body.get("montant");
        return compteService.deposer(numero, montant)
                .<ResponseEntity<?>>map(compte -> ResponseEntity
                        .ok(Map.of("message", "Dépôt effectué", "nouveauSolde", compte.getSolde())))
                .orElse(ResponseEntity.badRequest().body("Erreur : compte inexistant ou montant invalide"));
    }

    // POST /api/comptes/{numero}/retrait - Faire un retrait
    @PostMapping("/{numero}/retrait")
    public ResponseEntity<?> retirer(@PathVariable String numero, @RequestBody Map<String, Double> body) {
        Double montant = body.get("montant");
        return compteService.retirer(numero, montant)
                .<ResponseEntity<?>>map(compte -> ResponseEntity
                        .ok(Map.of("message", "Retrait effectué", "nouveauSolde", compte.getSolde())))
                .orElse(ResponseEntity.badRequest()
                        .body("Erreur : compte inexistant, montant invalide ou solde insuffisant"));
    }
}