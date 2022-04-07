package com.neon.releasetracker.services;

import com.neon.releasetracker.exceptions.CustomException;
import com.neon.releasetracker.models.Release;
import com.neon.releasetracker.models.ReleaseORM;
import com.neon.releasetracker.repositories.ReleaseRepositoryORM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class ReleaseServiceORM {

    @Autowired
    private ReleaseRepositoryORM releaseRepository;


    public List<ReleaseORM> getAllReleases() {
        return releaseRepository.findAll();
    }

    public ReleaseORM newRelease(ReleaseORM release) {
        release.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        release.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
        releaseRepository.save(release);
        return release;
    }

    public ReleaseORM getRelease(Integer id) throws CustomException {
        try {
            return releaseRepository.findById(id).orElseThrow(() -> new RuntimeException("Release doesn't exists"));
        } catch (RuntimeException e) {
            throw new CustomException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public ReleaseORM updateRelease(Integer id, ReleaseORM release) throws CustomException {
        ReleaseORM releaseORM = this.getRelease(id);
        releaseORM.setName(release.getName());
        releaseORM.setStatus(release.getStatus());
        releaseORM.setDescription(release.getDescription());
        releaseORM.setReleaseDate(release.getReleaseDate());
        releaseORM.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
        releaseRepository.save(releaseORM);
        return releaseORM;
    }

    public String deleteRelease(Integer id) throws CustomException {
        ReleaseORM releaseORM = this.getRelease(id);
        releaseRepository.delete(releaseORM);
        return "Successful";
    }
}
