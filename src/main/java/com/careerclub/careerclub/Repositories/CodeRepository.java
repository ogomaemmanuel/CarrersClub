package com.careerclub.careerclub.Repositories;

import com.careerclub.careerclub.Entities.Code;
import com.careerclub.careerclub.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepository extends JpaRepository<Code,Long> {
    Code findByUser(User user);
    void deleteAllByUserId(Long id);
}
