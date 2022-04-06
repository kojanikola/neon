package com.neon.releasetracker.services;

import com.neon.releasetracker.models.Release;
import com.neon.releasetracker.models.ReleaseORM;
import com.neon.releasetracker.repositories.ReleaseRepositoryORM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ReleaseServiceORM {

    @Autowired
    private ReleaseRepositoryORM releaseRepository;


    public List<ReleaseORM> getAllReleases() {
        return releaseRepository.findAll();
    }

    public ReleaseORM newRelease(ReleaseORM release) {
        release.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        releaseRepository.save(release);
        return release;
    }

    public ReleaseORM getRelease(Integer id) {
        return releaseRepository.getById(id);
    }

    public ReleaseORM updateRelease(Integer id, ReleaseORM release) {
        ReleaseORM releaseORM =  releaseRepository.getById(id);
        releaseORM.setName(release.getName());
        releaseORM.setStatus(release.getStatus());
        releaseORM.setDescription(release.getDescription());
        releaseORM.setReleaseDate(release.getReleaseDate());
        releaseORM.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
        releaseRepository.save(releaseORM);
        return releaseORM;
    }

    public String deleteRelease(Integer id) {
        ReleaseORM releaseORM =  releaseRepository.getById(id);
        releaseRepository.delete(releaseORM);
        return "Successful";
    }
}
