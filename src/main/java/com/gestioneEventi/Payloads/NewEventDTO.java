package com.gestioneEventi.Payloads;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record NewEventDTO(
        String nome,
        LocalDate data,
        int postiDisponibili,
        @NotNull(message = "L'ID dell'utente è obbligatorio")
        UUID organizzatore_id) {
}
