package it.epicode.examu5w3final.controller;

import it.epicode.examu5w3final.dto.LoginDto;
import it.epicode.examu5w3final.dto.UserDto;
import it.epicode.examu5w3final.exception.AlreadyExistException;
import it.epicode.examu5w3final.exception.NotFoundException;
import it.epicode.examu5w3final.exception.ValidationException;
import it.epicode.examu5w3final.model.User;
import it.epicode.examu5w3final.service.AuthService;
import it.epicode.examu5w3final.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
//
@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @PostMapping("/auth/register")
    public User register(@RequestBody @Validated UserDto userDto, BindingResult bindingResult) throws ValidationException,  AlreadyExistException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(s, e)->s+e));
        }
        return  userService.saveUser(userDto);
    }

    @GetMapping("/auth/login")
    public String login(@RequestBody @Validated LoginDto loginDto, BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(s,e)->s+e));
        }


        return authService.login(loginDto);
    }
}
