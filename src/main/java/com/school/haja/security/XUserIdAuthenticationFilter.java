package com.school.haja.security;

import com.school.haja.repository.UserRepository;
import com.school.haja.repository.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@AllArgsConstructor
public class XUserIdAuthenticationFilter extends OncePerRequestFilter {

  private final UserRepository userRepository;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String userIdHeader = request.getHeader("X-User-Id");

    if (userIdHeader != null && !userIdHeader.isBlank()) {
      try {
        UUID userId = UUID.fromString(userIdHeader);
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
          SimpleGrantedAuthority authority =
              new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
          UsernamePasswordAuthenticationToken authentication =
              new UsernamePasswordAuthenticationToken(user, null, List.of(authority));
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      } catch (IllegalArgumentException e) {
        // Invalid UUID format - leave SecurityContext empty
      }
    }

    filterChain.doFilter(request, response);
  }
}
