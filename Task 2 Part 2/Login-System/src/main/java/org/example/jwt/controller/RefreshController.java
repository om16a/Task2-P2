package org.example.jwt.controller;

import org.example.jwt.DTO.RefreshTokenDTO;
import org.example.jwt.model.RefreshToken;
import org.example.jwt.services.RefreshService;
import org.example.jwt.services.ValidateRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/refresh")
public class RefreshController {
    final ValidateRequestService validateRequestService;
    final RefreshService refreshService;

    public RefreshController(RefreshService refreshService, ValidateRequestService validateRequestService) {
        this.refreshService = refreshService;
        this.validateRequestService = validateRequestService;
    }
    @GetMapping("")
    public ResponseEntity<String> refreshAccessToken(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        if(!validateRequestService.validateRefreshRequest(refreshTokenDTO.getRefreshToken())) {
            System.out.println(refreshTokenDTO);
            return ResponseEntity.badRequest().build();
        }

        String accessToken = refreshService.refreshUserToken(refreshTokenDTO.getRefreshToken());
        return ResponseEntity.ok(accessToken);
    }
}
