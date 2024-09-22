package org.example.jwt.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "refresh-tokens")
@Data
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long jwtID;
    @Column(nullable = false, unique = true)
    String userEmail;
    @Column(nullable = false)
    Date createdAt;
    @Column(nullable = false)
    Date expiresAt;
    @Column(nullable = false, unique = true)
    String token;

    public RefreshToken(String userEmail, Date createdAt, Date expiresAt, String token) {
        this.userEmail = userEmail;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.token = token;
    }
}
