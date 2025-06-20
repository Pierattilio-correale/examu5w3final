package it.epicode.examu5w3final.service;

import it.epicode.examu5w3final.dto.EventoDto;
import it.epicode.examu5w3final.exception.NotFoundException;
import it.epicode.examu5w3final.model.Evento;
import it.epicode.examu5w3final.model.User;
import it.epicode.examu5w3final.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private UserService userService;


    public Evento saveEvento(EventoDto eventoDto ) throws NotFoundException {
        User user = userService.getUser(eventoDto.getUserId());
     Evento evento = new Evento();
     evento.setCreatore(user);
     evento.setData(eventoDto.getData());
     evento.setDescrizione(eventoDto.getDescrizione());
     evento.setTitolo(eventoDto.getTitolo());
     evento.setPostiTotali(eventoDto.getPostiTotali());
     evento.setPostiDisponibili(eventoDto.getPostiTotali());
evento.setLuogo(eventoDto.getLuogo());

return eventoRepository.save(evento);
    }

    public List<Evento> getListaEventi(){
        return eventoRepository.findAll();
    }

    public Evento getEvento(int id ) throws NotFoundException {
        return eventoRepository.findById(id).orElseThrow(()->new NotFoundException("l'evento con id "+id+" non è stato trovato"));
    }
    public Evento updateEvento (int id , EventoDto eventoDto) throws NotFoundException {
        Evento eventoDaAggiornare = getEvento(id);

        int prenotati = eventoDaAggiornare.getPrenotazioni().size();


        if (eventoDto.getPostiTotali() < prenotati) {
            throw new IllegalArgumentException("Non puoi impostare un numero di posti totali inferiore alle prenotazioni già effettuate (" + prenotati + ")");
        }
        eventoDaAggiornare.setLuogo(eventoDto.getLuogo());
        eventoDaAggiornare.setData(eventoDto.getData());
        eventoDaAggiornare.setDescrizione(eventoDto.getDescrizione());
        eventoDaAggiornare.setPostiTotali(eventoDto.getPostiTotali());
        eventoDaAggiornare.setTitolo(eventoDto.getTitolo());

        eventoDaAggiornare.setPostiDisponibili(eventoDto.getPostiTotali() - prenotati);

       return  eventoRepository.save(eventoDaAggiornare);
    }
    public void deleteEvento(int id) throws NotFoundException {
        Evento eventoDaEliminare= getEvento(id);
        eventoRepository.delete(eventoDaEliminare);
    }
}
