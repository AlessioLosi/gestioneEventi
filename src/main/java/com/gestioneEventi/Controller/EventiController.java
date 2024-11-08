package com.gestioneEventi.Controller;

import com.gestioneEventi.Entities.Event;
import com.gestioneEventi.Exceptions.BadRequestException;
import com.gestioneEventi.Payloads.NewEventDTO;
import com.gestioneEventi.Services.EventService;
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
@RequestMapping("/eventi")
public class EventiController {
    @Autowired
    private EventService es;

    @GetMapping
    public Page<Event> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String sortBy) {
        return this.es.findAll(page, size, sortBy);
    }

    @GetMapping("/{eventId}")
    public Event findById(@PathVariable UUID eventId) {
        return this.es.findById(eventId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('GESTORE_EVENTI')")
    @ResponseStatus(HttpStatus.CREATED)
    public Event save(@RequestBody @Validated NewEventDTO body, UUID organizzatore_id, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }

        return this.es.save(body, organizzatore_id);
    }

    @PutMapping("/{eventId}")
    @PreAuthorize("hasAuthority('GESTORE_EVENTI')")
    public Event findByIdAndUpdate(@PathVariable UUID eventId, @RequestBody @Validated NewEventDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload!");
        }
        return this.es.findByIdAndUpdate(eventId, body);
    }

    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasAuthority('GESTORE_EVENTI')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID eventId) {
        this.es.findByIdAndDelete(eventId);
    }

}
