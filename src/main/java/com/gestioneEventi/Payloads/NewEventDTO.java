package com.gestioneEventi.Payloads;

import java.time.LocalDate;
import java.util.UUID;

public record NewEventDTO(
        String nome,
        LocalDate data,
        int postiDisponibili,
        UUID organizzatore_id) {
}
