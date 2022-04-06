package com.neon.releasetracker.services;

import com.neon.releasetracker.models.Release;
import com.neon.releasetracker.repositories.ReleaseRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ReleaseService {

    @Autowired
    private ReleaseRepository releaseRepository;

    public Release newRelease(Release release) {
        release.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        release.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return releaseRepository.newRelease(release);
    }

    public List<Release> getAllReleases() {
        return releaseRepository.getAllReleases();
    }

    public Release getRelease(Integer id) {
        return releaseRepository.getRelease(id);
    }

    public Release updateRelease(Integer id, Release release) {
        release.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return releaseRepository.updateRelease(id, release);
    }

    public String deleteRelease(Integer id) {
        return releaseRepository.deleteRelease(id);
    }
}
