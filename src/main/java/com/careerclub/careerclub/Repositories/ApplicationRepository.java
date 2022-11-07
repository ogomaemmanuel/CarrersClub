package com.careerclub.careerclub.Repositories;

import com.careerclub.careerclub.Entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByJobIdAndUserId(Long jobId,Long usedId);
}
