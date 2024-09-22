package org.example.jwt.controller;

import org.example.jwt.DTO.UserDTO;
import org.example.jwt.repositories.UserRepository;
import org.example.jwt.services.SignupService;
import org.example.jwt.services.ValidateRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
public class SignupController {
    public final SignupService signupService;
    public final ValidateRequestService requestService;
    public SignupController(SignupService signupService, ValidateRequestService requestService) {
        this.signupService = signupService;
        this.requestService = requestService;
    }

    @PostMapping("")
    public ResponseEntity<String> signupUser(@RequestBody UserDTO userDTO) {
        if(!requestService.validateSignUp(userDTO)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        signupService.signupUser(userDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
