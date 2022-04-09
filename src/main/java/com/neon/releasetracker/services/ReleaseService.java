package com.neon.releasetracker.services;

import com.neon.releasetracker.exceptions.CustomException;
import com.neon.releasetracker.models.Release;
import com.neon.releasetracker.repositories.ReleaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
public class ReleaseService {

    @Autowired
    private ReleaseRepository releaseRepository;

    public Release newRelease(Release release) throws CustomException {
        release.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        release.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
        log.info("Current date and time is " + release.getCreatedAt());
        return releaseRepository.newRelease(release);
    }

    public List<Release> getAllReleases() throws CustomException {
        return releaseRepository.getAllReleases();
    }

    public Release getRelease(Integer id) throws CustomException {
        return releaseRepository.getRelease(id);
    }

    public Release updateRelease(Integer id, Release release) throws CustomException {
        release.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
        log.info("Current date and time is " + release.getLastUpdatedAt());
        return releaseRepository.updateRelease(id, release);
    }

    public String deleteRelease(Integer id) throws CustomException {
        return releaseRepository.deleteRelease(id);
    }

    public List<Release> getAllReleasesFilter(String status) throws CustomException {
        return releaseRepository.getAllReleasesFilter(status);
    }
}
