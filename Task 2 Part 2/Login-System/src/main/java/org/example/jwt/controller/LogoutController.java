package org.example.jwt.controller;

import org.example.jwt.services.LogoutService;
import org.example.jwt.services.ValidateRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logout")
public class LogoutController {
    final LogoutService logoutService;
    final ValidateRequestService validateRequestService;

    public LogoutController(LogoutService logoutService, ValidateRequestService validateRequestService) {
        this.logoutService = logoutService;
        this.validateRequestService = validateRequestService;
    }
    @GetMapping("")
    ResponseEntity<String> logoutUser(@RequestHeader(name = "Authorization") String accessToken) {
        accessToken = logoutService.extractBearerToken(accessToken);
        System.out.println(accessToken);

        if(!validateRequestService.validateLogout(accessToken)) {
            return ResponseEntity.badRequest().build();
        }

        logoutService.logoutUser(accessToken);
        return ResponseEntity.ok().build();
    }
}
