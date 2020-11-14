package com.usermangment.auth.repository;

import com.usermangment.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    User getUserByUserId(Long userId);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    List<User> getAllByIsCustomer(String isCustomer);
}