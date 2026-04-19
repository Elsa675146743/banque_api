package com.banque.banque_api.repository;

import com.banque.banque_api.entity.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CompteRepository extends JpaRepository<Compte, Long> {
    Optional<Compte> findByNumeroCompte(String numeroCompte);
}