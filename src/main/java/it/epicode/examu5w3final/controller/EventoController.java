package it.epicode.examu5w3final.controller;

import it.epicode.examu5w3final.dto.EventoDto;
import it.epicode.examu5w3final.exception.NotFoundException;
import it.epicode.examu5w3final.exception.ValidationException;
import it.epicode.examu5w3final.model.Evento;
import it.epicode.examu5w3final.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventi")
public class EventoController {
    @Autowired
    private EventoService eventoService;

    @PostMapping("")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public Evento creaEvento(@RequestBody  @Validated EventoDto eventoDto , BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(e, s)->s+e));

        }
        return eventoService.saveEvento(eventoDto);

    }
    @GetMapping("")

   public List<Evento>  getListaEventi(){
        return  eventoService.getListaEventi();
   }
    @GetMapping("/{id}")

    public Evento getEvento(@PathVariable  int id) throws NotFoundException {
        return eventoService.getEvento(id);
    }
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @PutMapping("/{id}")
    public Evento aggiornaEvento(@PathVariable int id , @RequestBody @Validated EventoDto eventoDto, BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(e, s)->s+e));

        }
        return eventoService.updateEvento(id, eventoDto);

    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public void deleteEvento(@PathVariable int id ) throws NotFoundException {
        eventoService.deleteEvento(id);
    }

}
