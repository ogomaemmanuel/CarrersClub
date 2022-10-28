package com.careerclub.careerclub.Repositories;

import com.careerclub.careerclub.Entities.Job;
import com.careerclub.careerclub.Report.JobsByLocationReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Query(value = "SELECT l.name as location, count(l.name) as totalJobs  from job_posting j left join locations l on j.location_id = l.id where (l.name is null) =0 group by l.name", nativeQuery = true)
    public List<JobsByLocationReports> getJobsByLocations();

    public List<Job> findAllByCompanyId(Long companyId);
}
