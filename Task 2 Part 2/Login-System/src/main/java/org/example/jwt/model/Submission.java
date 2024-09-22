package org.example.jwt.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "Problem")
@NoArgsConstructor
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long submissionId;

    @ManyToOne
    @JoinColumn(name = "problemId")
    Problem problemId;

    @Column(nullable = false)
    Long languageId;

    @ManyToOne
    @JoinColumn(name = "userId")
    User userId;
    @Column
    String userCode;
    @Column
    String status;
    @Column
    String token;
    @Column
    Date sumbitTime;
    @Column
    Date lastTokenDate;





}
