package it.epicode.examu5w3final.service;

import it.epicode.examu5w3final.dto.PrenotazioneDto;
import it.epicode.examu5w3final.exception.NotFoundException;
import it.epicode.examu5w3final.exception.UnauthoraizedException;
import it.epicode.examu5w3final.model.Evento;
import it.epicode.examu5w3final.model.Prenotazione;
import it.epicode.examu5w3final.model.User;
import it.epicode.examu5w3final.repository.EventoRepository;
import it.epicode.examu5w3final.repository.PrenotazioneRepository;
import it.epicode.examu5w3final.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//
import java.util.List;

@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private EventoService eventoService;

    public Prenotazione savePrenotazione(PrenotazioneDto prenotazioneDto) throws NotFoundException {
        User user = userRepository.findById(prenotazioneDto.getUserId())
                .orElseThrow(() -> new NotFoundException("L'utente non è stato trovato"));

        Evento evento = eventoRepository.findById(prenotazioneDto.getEventoId())
                .orElseThrow(() -> new NotFoundException("L'evento non è stato trovato"));


        if (evento.getPostiDisponibili() <= 0) {
            throw new IllegalStateException("Non ci sono posti disponibili per questo evento.");
        }


        boolean giaPrenotato = evento.getPrenotazioni().stream()
                .anyMatch(p -> p.getUtente().getId() == user.getId());

        if (giaPrenotato) {
            throw new IllegalStateException("Hai già prenotato questo evento.");
        }


        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setEvento(evento);
        prenotazione.setUtente(user);


        evento.setPostiDisponibili(evento.getPostiDisponibili() - 1);
        eventoRepository.save(evento);

        return prenotazioneRepository.save(prenotazione);
    }
    public List<Prenotazione>getListaPrenotazioni(){
        return prenotazioneRepository.findAll();
    }

    public Prenotazione getPrenotazione(int id) throws NotFoundException {
        return prenotazioneRepository.findById(id).orElseThrow(()-> new NotFoundException("la prenotazione con id "+id+" non è stata trovata"));

    }

    public Prenotazione updatePrenotazione(int id , PrenotazioneDto prenotazioneDto) throws NotFoundException {
        Prenotazione prenotazioneDaAggiornare= getPrenotazione(id);

        if(prenotazioneDaAggiornare.getUtente().getId() != prenotazioneDto.getUserId()){
            User user= userService.getUser(prenotazioneDto.getUserId());
            prenotazioneDaAggiornare.setUtente(user);
        }

        if(prenotazioneDaAggiornare.getEvento().getId() != prenotazioneDto.getEventoId()){
           Evento evento= eventoService.getEvento(prenotazioneDto.getEventoId());
            prenotazioneDaAggiornare.setEvento(evento);
        }
       return prenotazioneRepository.save(prenotazioneDaAggiornare);

    }

    public void deletePrenotazione(int prenotazioneId, int userId) throws NotFoundException {
        Prenotazione prenotazione = prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(() -> new NotFoundException("Prenotazione non trovata"));

        if (prenotazione.getUtente().getId() != userId) {
            throw new UnauthoraizedException("Non puoi annullare prenotazioni altrui");
        }

        Evento evento = prenotazione.getEvento();
        evento.setPostiDisponibili(evento.getPostiDisponibili() + 1);
        eventoRepository.save(evento);

        prenotazioneRepository.delete(prenotazione);
    }
}
