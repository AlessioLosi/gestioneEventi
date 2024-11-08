package com.gestioneEventi.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "prenotazioni")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Prenotazioni {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private LocalDate data;
    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User utente;

    public Prenotazioni(LocalDate data, Event event, User utente) {
        this.data = data;
        this.event = event;
        this.utente = utente;
    }
}
