package com.gestioneEventi.Services;

import com.gestioneEventi.Entities.Event;
import com.gestioneEventi.Entities.User;
import com.gestioneEventi.Exceptions.BadRequestException;
import com.gestioneEventi.Exceptions.NotFoundException;
import com.gestioneEventi.Payloads.NewEventDTO;
import com.gestioneEventi.Repositories.EventRepository;
import com.gestioneEventi.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventService {
    @Autowired
    private EventRepository eR;
    @Autowired
    private UserRepository uR;

    public Event save(NewEventDTO body, UUID organizzatore_id) {
        this.eR.findByNome(body.nome()).ifPresent(
                user -> {
                    throw new BadRequestException("Evento esistente");
                }
        );
        User organizzatore = uR.findById(organizzatore_id)
                .orElseThrow(() -> new NotFoundException("Organizzatore  non trovato"));
        Event newEvent = new Event(body.nome(), body.postiDisponibili(), body.data(), organizzatore);
        return this.eR.save(newEvent);
    }

    public Page<Event> findAll(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.eR.findAll(pageable);
    }

    public Event findById(UUID eventId) {
        return this.eR.findById(eventId).orElseThrow(() -> new NotFoundException(eventId));
    }

    public Event findByIdAndUpdate(UUID eventId, NewEventDTO body) {
        Event found = this.findById(eventId);

        if (!found.getNome().equals(body.nome())) {
            this.eR.findByNome(body.nome()).ifPresent(
                    user -> {
                        throw new BadRequestException("Evento esistente");
                    }
            );
        }

        found.setNome(body.nome());
        found.setData(body.data());
        found.setPostiDisponibili(body.postiDisponibili());


        return this.eR.save(found);
    }

    public void findByIdAndDelete(UUID eventId) {
        Event found = this.findById(eventId);
        this.eR.delete(found);
    }

    public Event findByName(String name) {
        return this.eR.findByNome(name).orElseThrow(() -> new NotFoundException("Evento non trovato "));
    }

}
