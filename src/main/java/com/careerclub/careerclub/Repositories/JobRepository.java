package com.careerclub.careerclub.Repositories;

import com.careerclub.careerclub.Entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findAllByCompanyId(Long id);
}
