package com.neon.releasetracker.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ReleaseController {

    @GetMapping(value="/releases")
    public ResponseEntity<String> getVehicles() {
        return new ResponseEntity("releases", HttpStatus.OK);
    }

}
