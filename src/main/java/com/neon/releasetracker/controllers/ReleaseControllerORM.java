package com.neon.releasetracker.controllers;

import com.neon.releasetracker.exceptions.CustomException;
import com.neon.releasetracker.models.Release;
import com.neon.releasetracker.models.ReleaseORM;
import com.neon.releasetracker.services.ReleaseService;
import com.neon.releasetracker.services.ReleaseServiceORM;
import com.neon.releasetracker.validators.StatusValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orm")
public class ReleaseControllerORM {

    @Autowired
    private ReleaseServiceORM releaseService;

    @Autowired
    private StatusValidator statusValidator;

    @GetMapping(value = "/releases")
    public ResponseEntity<List<Release>> getAllReleases() {
        return new ResponseEntity(releaseService.getAllReleases(), HttpStatus.OK);
    }

    @GetMapping(value = "/releases/{id}")
    public ResponseEntity<Release> getRelease(@PathVariable Integer id) {
        return new ResponseEntity(releaseService.getRelease(id), HttpStatus.OK);
    }

    @GetMapping(value = "/release")
    public ResponseEntity<Release> getReleaseRequestParam(@RequestParam Integer id) {
        return new ResponseEntity(releaseService.getRelease(id), HttpStatus.OK);
    }

    @PostMapping(value = "/releases")
    public ResponseEntity<Release> newRelease(@RequestBody ReleaseORM release) throws CustomException {
        statusValidator.checkStatus(release.getStatus());
        return new ResponseEntity(releaseService.newRelease(release), HttpStatus.OK);
    }

    @PutMapping(value = "/releases/{id}")
    public ResponseEntity<ReleaseORM> updateRelease(@PathVariable Integer id, @RequestBody ReleaseORM release) throws CustomException {
        statusValidator.checkStatus(release.getStatus());
        return new ResponseEntity(releaseService.updateRelease(id, release), HttpStatus.OK);
    }

    @DeleteMapping(value = "/releases/{id}")
    public ResponseEntity<String> deleteRelease(@PathVariable Integer id) {
        return new ResponseEntity(releaseService.deleteRelease(id), HttpStatus.OK);
    }
}
