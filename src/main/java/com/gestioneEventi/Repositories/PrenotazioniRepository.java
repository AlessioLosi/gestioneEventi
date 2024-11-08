package com.gestioneEventi.Repositories;

import com.gestioneEventi.Entities.Prenotazioni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrenotazioniRepository extends JpaRepository<Prenotazioni, UUID> {
    Optional<Prenotazioni> findById(Long id);
}
