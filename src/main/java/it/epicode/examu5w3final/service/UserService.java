package it.epicode.examu5w3final.service;

import it.epicode.examu5w3final.dto.UserDto;
import it.epicode.examu5w3final.enumerated.Role;
import it.epicode.examu5w3final.exception.AlreadyExistException;
import it.epicode.examu5w3final.exception.NotFoundException;
import it.epicode.examu5w3final.model.User;
import it.epicode.examu5w3final.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
//
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(UserDto userDto) throws AlreadyExistException {

        User user= new User();
        if(userRepository.existsByUsername(userDto.getUsername())) {
            throw new AlreadyExistException("Username già esistente");
        }
        if(userRepository.existsByEmail(userDto.getEmail())) {
            throw new AlreadyExistException("email già esistente");
        }
        user.setNome(userDto.getNome());
        user.setCognome(userDto.getCognome());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setRole(Role.USER);

        return userRepository.save(user);

    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User getUser(int id)throws NotFoundException {
        return userRepository.findById(id).orElseThrow(()->new NotFoundException("L'utente con ID " + id + " non è stato trovato"));
    }


    public User updateUser(int id , UserDto userDto) throws NotFoundException{
        User userDaAggiornare = getUser(id);


        userDaAggiornare.setNome(userDto.getNome());
        userDaAggiornare.setUsername(userDto.getUsername());
        userDaAggiornare.setCognome(userDto.getCognome());
        userDaAggiornare.setEmail(userDto.getEmail());
        if(!passwordEncoder.matches(userDto.getPassword(), userDaAggiornare.getPassword())){
            userDaAggiornare.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }


        return userRepository.save(userDaAggiornare);
    }

    public void deleteUser(int id)throws NotFoundException{
        User userDaEliminare = getUser(id);
        userRepository.delete(userDaEliminare);
    }
}

