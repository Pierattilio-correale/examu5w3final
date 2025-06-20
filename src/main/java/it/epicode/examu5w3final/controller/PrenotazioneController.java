package it.epicode.examu5w3final.controller;

import it.epicode.examu5w3final.dto.PrenotazioneDto;
import it.epicode.examu5w3final.exception.NotFoundException;
import it.epicode.examu5w3final.exception.ValidationException;
import it.epicode.examu5w3final.model.Prenotazione;
import it.epicode.examu5w3final.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {
    @Autowired
    private PrenotazioneService prenotazioneService;

    @PostMapping("")
    public Prenotazione creaPrenotazione(@RequestBody @Validated PrenotazioneDto prenotazioneDto , BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(e,s)->s+e));

        }
        return prenotazioneService.savePrenotazione(prenotazioneDto);

    }
    @GetMapping("")
    public List<Prenotazione> getListaPrenotazioni(){
       return  prenotazioneService.getListaPrenotazioni();
    }
    @GetMapping("/{id}")
    public Prenotazione getPrenotazione(@PathVariable int id ) throws NotFoundException {
        return  prenotazioneService.getPrenotazione(id);

    }
    @PutMapping("/{id}")
    public Prenotazione aggiornaPrenotazione(@PathVariable int id ,@RequestBody @Validated PrenotazioneDto prenotazioneDto, BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(e, s)->e+s));

        }
return prenotazioneService.updatePrenotazione(id,prenotazioneDto);
    }
    @DeleteMapping("/{id}")
    public void deletePrenotazione(@PathVariable int id, @RequestParam int userId) throws NotFoundException {
        System.out.println("prenotazione annullata con successo");
        prenotazioneService.deletePrenotazione(id, userId);

    }
}
