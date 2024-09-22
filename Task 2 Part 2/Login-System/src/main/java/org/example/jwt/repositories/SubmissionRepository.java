package org.example.jwt.repositories;

import org.example.jwt.model.Submission;
import org.example.jwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Optional<Submission> findBySubmissionId(Long submissionId);
    List<Submission> findByUserId(User user);

}
