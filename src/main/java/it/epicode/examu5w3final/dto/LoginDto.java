package it.epicode.examu5w3final.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
//
@Data
public class LoginDto {
    @NotEmpty(message = "l''username non può essere nullo o vuoto!")
    private String username;
    @NotEmpty(message = "la password non può essere nulla o vuota!")
    private String password;
    @Email(message = "deve essere un indirizzo email valido!")
    private String email;
}
