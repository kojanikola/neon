package com.neon.releasetracker.repositories;

import com.neon.releasetracker.models.ReleaseORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseRepositoryORM extends JpaRepository<ReleaseORM, Integer> {
}
