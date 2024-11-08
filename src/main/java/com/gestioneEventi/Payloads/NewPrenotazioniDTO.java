package com.gestioneEventi.Payloads;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record NewPrenotazioniDTO(
        LocalDate data_di_richiesta,

        @NotNull(message = "L'ID dell'utente è obbligatorio")
        UUID utente_id,

        @NotNull(message = "L'ID dell'evento  è obbligatorio")
        UUID evento_id) {
}
