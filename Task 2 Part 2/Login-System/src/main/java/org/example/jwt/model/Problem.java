package org.example.jwt.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "Problem")
@NoArgsConstructor
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long problemId;
    @Column(nullable = false)
    String statement;
    @Column(nullable = false)
    String input;
    @Column(nullable = false)
    Date output;
    @Column(nullable = false)
    Float timeLimit;
    @Column(nullable = false)
    Float memoryLimit;


}
