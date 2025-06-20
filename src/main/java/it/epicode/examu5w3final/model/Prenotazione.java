package it.epicode.examu5w3final.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Prenotazione {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private User utente;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;
}

