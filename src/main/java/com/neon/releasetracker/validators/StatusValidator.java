package com.neon.releasetracker.validators;

import com.neon.releasetracker.exceptions.CustomException;
import com.neon.releasetracker.repositories.ReleaseRepository;
import com.neon.releasetracker.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatusValidator {

    @Autowired
    private StatusRepository statusRepository;

    public String checkStatus(String status) throws CustomException {
        return statusRepository.checkStatus(status);
    }

}
