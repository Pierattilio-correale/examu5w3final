package it.epicode.examu5w3final.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class EventoDto {
    @NotEmpty(message = "il titolo non può essere nullo o vuoto")
    private String titolo;

    @NotEmpty(message = "la descrizione non può essere nulla o vuota")
    private String descrizione;

    @NotEmpty(message = "il luogo non può essere nullo o vuoto")
    private String luogo;

    @NotNull(message = "la data non può essere nulla o assente")
    private LocalDateTime data;

    @NotNull(message = "devi segnalare quanti posti totali possiede!")
    private int postiTotali;


}
