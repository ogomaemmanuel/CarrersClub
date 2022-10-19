package com.careerclub.careerclub.Repositories;

import com.careerclub.careerclub.Entities.CodeAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CodeAttemptRepository extends JpaRepository<CodeAttempt, Long> {
    @Query(value = "select count(*) from code_attempts where user_id=:userId and created_at>=:time", nativeQuery = true)
    Long countCodeAttemptByUserOrderByCreatedAt(Long userId, LocalDateTime time);
}
