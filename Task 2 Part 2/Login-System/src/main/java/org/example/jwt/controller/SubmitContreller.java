package org.example.jwt.controller;

import org.example.jwt.DTO.SubmissionDTO;
import org.example.jwt.services.SubmitService;
import org.example.jwt.services.ValidateRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/submit/{problemId}")
public class SubmitController {
    final ValidateRequestService validateRequestService;
    final SubmitService submitService;

    public SubmitController(ValidateRequestService validateRequestService, SubmitService submitService) {
        this.validateRequestService = validateRequestService;
        this.submitService = submitService;
    }
    @PostMapping("")
    public ResponseEntity<?> submitSolution(@RequestHeader(name = "Authorization") String accessToken, @RequestBody SubmissionDTO submissionDTO, @PathVariable Long problemId ) {

        if(!validateRequestService.validateUserInfoRequest(accessToken)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

       submissionDTO.setProblemId(problemId);
        submissionDTO.setUserId();
        if(!validateRequestService.validateSubmit(submissionDTO)) {

        }
    }
}