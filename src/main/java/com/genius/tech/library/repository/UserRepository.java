package com.genius.tech.library.repository;

import com.genius.tech.library.enums.Role;
import com.genius.tech.library.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    /** Highest numeric suffix for a given role prefix, e.g. "STU" → max(STU-001..STU-999) */
    @Query("SELECT MAX(u.userCode) FROM User u WHERE u.role = :role")
    Optional<String> findMaxUserCodeByRole(Role role);
}
