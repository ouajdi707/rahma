package com.esprit.spring.ftthback.repository;



import com.esprit.spring.ftthback.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User findUserByUsername(String username);
    Optional<User> findByEmail(String email);
}
