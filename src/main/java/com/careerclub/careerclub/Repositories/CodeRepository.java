package com.careerclub.careerclub.Repositories;

import com.careerclub.careerclub.Entities.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepository extends JpaRepository<Code,Long> {
}
