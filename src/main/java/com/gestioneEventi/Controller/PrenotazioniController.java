package com.gestioneEventi.Controller;

import com.gestioneEventi.Entities.Event;
import com.gestioneEventi.Entities.Prenotazioni;
import com.gestioneEventi.Exceptions.BadRequestException;
import com.gestioneEventi.Payloads.NewPrenotazioniDTO;
import com.gestioneEventi.Services.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioniController {
    @Autowired
    private PrenotazioneService pS;

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public Page<Event> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String sortBy) {
        return this.pS.findAll(page, size, sortBy);
    }

    @GetMapping("/{prenotazioniId}")
    @PreAuthorize("hasAuthority('USER')")
    public Prenotazioni findById(@PathVariable UUID eventId) {
        return this.pS.findById(eventId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazioni save(@RequestBody @Validated NewPrenotazioniDTO body, UUID evento, UUID utente, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }

        return this.pS.save(body, evento, utente);
    }

    @PutMapping("/{prenotazioniId}")
    @PreAuthorize("hasAuthority('USER')")
    public Prenotazioni findByIdAndUpdate(@PathVariable UUID prenotazioniId, @RequestBody @Validated NewPrenotazioniDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload!");
        }
        return this.pS.findByIdAndUpdate(prenotazioniId, body);
    }

    @DeleteMapping("/{prenotazioniId}")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID prenotazioneId) {
        this.pS.findByIdAndDelete(prenotazioneId);
    }

}
