package it.epicode.examu5w3final.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Evento {
        @Id
        @GeneratedValue
        private Long id;

        private String titolo;
        private String descrizione;
        private String luogo;
        private LocalDateTime data;

        private int postiTotali;
        private int postiDisponibili;

        @ManyToOne
        @JoinColumn(name = "creatore_id")
        private User creatore;

        @OneToMany(mappedBy = "evento")
    private List<Prenotazione>prenotazioni;
    }

