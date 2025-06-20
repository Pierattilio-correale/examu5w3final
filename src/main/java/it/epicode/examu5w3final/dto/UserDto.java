package it.epicode.examu5w3final.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
//
@Data
public class UserDto {
    @NotEmpty(message = "l' username non può essere nullo o vuoto!")
    private String username;
    @NotEmpty(message = "il nome non può essere nullo o vuoto!")
    private String nome;
    @NotEmpty(message = "il cognome non può essere nullo o vuoto!")
    private   String cognome;
    @Email(message = "l'email non può essere nulla o vuota!")
    private String email;
    @NotEmpty(message = "la password non può essere nulla o vuota!")
    private String password;
}
