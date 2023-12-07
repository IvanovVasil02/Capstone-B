package com.ivanovvasil.CapstoneB.security;

import com.ivanovvasil.CapstoneB.exceptions.UnauthorizedException;
import com.ivanovvasil.CapstoneB.user.User;
import com.ivanovvasil.CapstoneB.user.services.UsersService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
  @Autowired
  private JWTTools jwtTools;

  @Autowired
  private UsersService userService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new UnauthorizedException("Athorization Bearer token not found.");
    } else {
      String token = authHeader.substring(7);
      jwtTools.verifyToken(token);
      String id = jwtTools.extractIdFroToken(token);
      User currentUser = userService.findById(UUID.fromString(id));
      Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      filterChain.doFilter(request, response);
    }
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return new AntPathMatcher().match("/authentication/**", request.getServletPath());
  }
}
