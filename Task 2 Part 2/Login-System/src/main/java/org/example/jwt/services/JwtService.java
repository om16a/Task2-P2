package org.example.jwt.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.example.jwt.model.RefreshToken;
import org.example.jwt.repositories.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.access.secret}")
    String jwtAccessSecret;
    @Value("${jwt.refresh.secret}")
    String jwtRefreshSecret;
    @Value("${jwt.access-token.expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.refresh-token.expiration}")
    private Long refreshTokenExpiration;
    final RefreshTokenRepository refreshTokenRepo;

    public JwtService(RefreshTokenRepository refreshTokenRepo) {
        this.refreshTokenRepo = refreshTokenRepo;
    }

    private Key getSigningAccessKey() {
        byte[] keyBytes = jwtAccessSecret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private Key getSigningRefreshKey() {
        byte[] keyBytes = jwtRefreshSecret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(String userEmail) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userEmail, accessTokenExpiration, getSigningAccessKey());
    }
    public String generateRefreshToken(String userEmail) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userEmail, refreshTokenExpiration, getSigningRefreshKey());
    }
    private String createToken(Map<String, Object> claims, String subject
            , Long expiration, Key key) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public String extractUsername(String token, Key key) {
        return extractClaim(token, key, Claims::getSubject);
    }

    public String extractSubject(String token, Key key) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
    public String extractSubjectRefreshToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningRefreshKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
    public String extractSubjectAccessToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningAccessKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
    public Date extractExpiryDateRefreshToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningRefreshKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }
    public Date extractIssuedAtRefreshToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningRefreshKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getIssuedAt();
    }
    public Date extractExpiryDateAccessToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningAccessKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }
    public Date extractIssuedAtAccessToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningAccessKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getIssuedAt();
    }

    public Date extractExpiration(String token, Key key) {
        return extractClaim(token, key, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Key key, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token, key);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, Key key) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token, Key key) {
        return extractExpiration(token, key).before(new Date());
    }

    public Boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningAccessKey()).build().parseClaimsJws(token);
            return !isTokenExpired(token, getSigningAccessKey());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    @Transactional
    public Boolean validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningRefreshKey()).build().parseClaimsJws(token);
            boolean isNotExpired = !isTokenExpired(token, getSigningRefreshKey());
            if(!isNotExpired)
                refreshTokenRepo.deleteByToken(token);
            return isNotExpired;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findRefreshTokenByToken(token);
    }

    @Transactional
    public void deleteRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepo.deleteByUserEmail(refreshToken.getUserEmail());
    }

}
