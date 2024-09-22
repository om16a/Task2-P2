package org.example.jwt.controller;

import org.example.jwt.DTO.UserDTO;
import org.example.jwt.model.User;
import org.example.jwt.services.UserInfoService;
import org.example.jwt.services.ValidateRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    final ValidateRequestService validateRequestService;
    final UserInfoService userInfoService;

    public UserInfoController(ValidateRequestService validateRequestService, UserInfoService userInfoService) {
        this.validateRequestService = validateRequestService;
        this.userInfoService = userInfoService;
    }
    @GetMapping("")
    public ResponseEntity<UserDTO> getUserInfo(@RequestHeader(name = "Authorization") String accessToken) {
        accessToken = userInfoService.extractBearerToken(accessToken);

        if(!validateRequestService.validateUserInfoRequest(accessToken)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        UserDTO userInfo = userInfoService.getUserInfo(accessToken);
        return ResponseEntity.ok(userInfo);
    }
}
