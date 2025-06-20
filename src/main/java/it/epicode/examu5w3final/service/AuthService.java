package it.epicode.examu5w3final.service;

import it.epicode.examu5w3final.dto.LoginDto;
import it.epicode.examu5w3final.exception.NotFoundException;
import it.epicode.examu5w3final.model.User;
import it.epicode.examu5w3final.repository.UserRepository;
import it.epicode.examu5w3final.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTool jwtTool;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public  String login(LoginDto loginDto) throws NotFoundException {
        User user =userRepository.findByUsernameAndEmail((loginDto.getUsername()) , loginDto.getEmail()).orElseThrow(()->new NotFoundException("l'utente con questo username/password non esiste"));
        if((passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))){


            return jwtTool.createToken(user);
        }else{
            throw new NotFoundException("l'utente con questo username/password non esiste");
        }
    }
}
