package com.careerclub.careerclub.Repositories;

import com.careerclub.careerclub.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Page<User> findAll(Pageable pageable);
    Optional<User> findByUsername(String username);
    User findByEmail(String email);
}
