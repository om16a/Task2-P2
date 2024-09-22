package org.example.jwt.controller;

import org.example.jwt.DTO.UserDTO;
import org.example.jwt.model.TokenPair;
import org.example.jwt.services.JwtService;
import org.example.jwt.services.LoginService;
import org.example.jwt.services.ValidateRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    final ValidateRequestService validateRequestService;
    final LoginService loginService;
    public LoginController(ValidateRequestService validateRequestService, LoginService loginService) {
        this.validateRequestService = validateRequestService;
        this.loginService = loginService;
    }

    @PostMapping("")
    public ResponseEntity<TokenPair> loginUser(@RequestBody UserDTO userDTO) {
        if(!validateRequestService.validateLogin(userDTO)) {
            System.out.println(userDTO);
            return ResponseEntity.badRequest().build();
        }

        TokenPair tokenPair = loginService.loginUser(userDTO);
        return ResponseEntity.ok(tokenPair);
    }
}
