package com.neon.releasetracker.controllers;

import com.neon.releasetracker.exceptions.CustomException;
import com.neon.releasetracker.models.Release;
import com.neon.releasetracker.services.ReleaseService;
import com.neon.releasetracker.validators.StatusValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class ReleaseController {

    @Autowired
    private ReleaseService releaseService;

    @Autowired
    private StatusValidator statusValidator;

    @GetMapping(value = "/releases")
    public ResponseEntity<List<Release>> getAllReleases() throws CustomException {
        log.info("Getting all releases");
        return new ResponseEntity(releaseService.getAllReleases(), HttpStatus.OK);
    }

    @GetMapping(value = "/releases/filter")
    public ResponseEntity<List<Release>> getAllReleasesFilter(@RequestParam String status) throws CustomException {
        log.info("Getting all releases with same status. Status = "+ status);
        return new ResponseEntity(releaseService.getAllReleasesFilter(status), HttpStatus.OK);
    }

    @GetMapping(value = "/releases/{id}")
    public ResponseEntity<Release> getRelease(@PathVariable Integer id) throws CustomException {
        log.info("Getting release with id = " + id);
        return new ResponseEntity(releaseService.getRelease(id), HttpStatus.OK);
    }

    @GetMapping(value = "/release")
    public ResponseEntity<Release> getReleaseRequestParam(@RequestParam Integer id) throws CustomException {
        return new ResponseEntity(releaseService.getRelease(id), HttpStatus.OK);
    }

    @PostMapping(value = "/releases")
    public ResponseEntity<Release> newRelease(@RequestBody Release release) throws CustomException {
        log.info("Validating status: " + release.getStatus());
        statusValidator.checkStatus(release.getStatus());
        log.info("Insert new Release " + release.getName());
        return new ResponseEntity(releaseService.newRelease(release), HttpStatus.OK);
    }

    @PutMapping(value = "/releases/{id}")
    public ResponseEntity<Release> updateRelease(@PathVariable Integer id, @RequestBody Release release) throws CustomException {
        log.info("Validating status: " + release.getStatus());
        statusValidator.checkStatus(release.getStatus());
        log.info("Update Release " + release.getName());
        return new ResponseEntity(releaseService.updateRelease(id, release), HttpStatus.OK);
    }

    @DeleteMapping(value = "/releases/{id}")
    public ResponseEntity<String> deleteRelease(@PathVariable Integer id) throws CustomException {
        log.info("Delete Release with id = " + id);
        return new ResponseEntity(releaseService.deleteRelease(id), HttpStatus.OK);
    }
}
