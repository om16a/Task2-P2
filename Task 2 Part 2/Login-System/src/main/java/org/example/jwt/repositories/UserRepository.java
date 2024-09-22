package org.example.jwt.repositories;

import org.example.jwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserEmail(String userEmail);
    Optional<User> findUserByUserEmailAndPassword(String userEmail, String password);
}
