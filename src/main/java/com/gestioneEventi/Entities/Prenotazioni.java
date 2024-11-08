package com.gestioneEventi.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "prenotazioni")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Prenotazioni {
    @Id
    private Long id;
    private LocalDate data;
    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User utente;

    public Prenotazioni(Long id, LocalDate data, Event event, User utente) {
        this.id = id;
        this.data = data;
        this.event = event;
        this.utente = utente;
    }
}
