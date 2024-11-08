package com.gestioneEventi.Services;

import com.gestioneEventi.Entities.Event;
import com.gestioneEventi.Entities.Prenotazioni;
import com.gestioneEventi.Entities.User;
import com.gestioneEventi.Exceptions.BadRequestException;
import com.gestioneEventi.Exceptions.NotFoundException;
import com.gestioneEventi.Payloads.NewPrenotazioniDTO;
import com.gestioneEventi.Repositories.EventRepository;
import com.gestioneEventi.Repositories.PrenotazioniRepository;
import com.gestioneEventi.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioniRepository pR;
    @Autowired
    private EventRepository eR;
    @Autowired
    private UserRepository uR;

    public Prenotazioni save(NewPrenotazioniDTO body, UUID utenteId, UUID eventoId) {
        this.pR.findById(body.Id()).ifPresent(
                user -> {
                    throw new BadRequestException("Prenotazione esistente");
                }
        );
        User utente = uR.findById(utenteId)
                .orElseThrow(() -> new NotFoundException("Utente  non trovato"));
        Event evento = eR.findById(eventoId)
                .orElseThrow(() -> new NotFoundException("Evento non trovato"));
        Prenotazioni newPrenotazione = new Prenotazioni(body.Id(), body.data(), evento, utente);
        return this.pR.save(newPrenotazione);
    }

    public Page<Event> findAll(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.eR.findAll(pageable);
    }

    public Prenotazioni findById(UUID prenotazioneId) {
        return this.pR.findById(prenotazioneId).orElseThrow(() -> new NotFoundException(prenotazioneId));
    }

    public Prenotazioni findByIdAndUpdate(UUID prenotazioniId, NewPrenotazioniDTO body) {
        Prenotazioni found = this.findById(prenotazioniId);


        found.setData(body.data());


        return this.pR.save(found);
    }

    public void findByIdAndDelete(UUID prenotazioniId) {
        Prenotazioni found = this.findById(prenotazioniId);
        this.pR.delete(found);
    }

    public Prenotazioni findById(Long Id) {
        return this.pR.findById(Id).orElseThrow(() -> new NotFoundException("Prenotazione non trovata"));
    }
}
