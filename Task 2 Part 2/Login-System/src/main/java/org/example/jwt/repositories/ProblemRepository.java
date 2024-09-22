package org.example.jwt.repositories;

import org.example.jwt.model.Problem;
import org.example.jwt.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    Optional<Problem>  findByProblemId(Long problemId);
}
