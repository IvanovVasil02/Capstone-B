package com.ivanovvasil.CapstoneB.security;

import com.ivanovvasil.CapstoneB.exceptions.UnauthorizedException;
import com.ivanovvasil.CapstoneB.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTTools {
  @Value("${spring.jwt.secret}")
  private String secret;

  public String createToken(User user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("role", user.getRole());
    claims.put("id", user.getId());
    return Jwts.builder()
            .setSubject(String.valueOf(user.getId()))
            .setClaims(claims)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
            .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
            .compact();
  }

  public void verifyToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(
                      Keys.hmacShaKeyFor(secret.getBytes()))
              .build().parse(token);
    } catch (Exception e) {
      throw new UnauthorizedException(("Invalid token, please log in"));
    }
  }

  public String extractIdFroToken(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
            .build()
            .parseClaimsJws(token)
            .getBody().get("id").toString();
  }

}
