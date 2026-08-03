package com.school.haja.service;

import com.school.haja.endpoint.rest.controller.exception.ForbiddenException;
import com.school.haja.repository.UserRepository;
import com.school.haja.repository.model.User;
import com.school.haja.repository.model.UserRole;
import java.util.Arrays;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserAuthService {

  private final UserRepository userRepository;

  public User validateUserAndRole(String userIdHeader, UserRole... allowedRoles) {
    if (userIdHeader == null || userIdHeader.isBlank()) {
      throw new ForbiddenException("Missing authentication header X-User-Id");
    }
    UUID userId;
    try {
      userId = UUID.fromString(userIdHeader);
    } catch (IllegalArgumentException e) {
      throw new ForbiddenException("Invalid X-User-Id format");
    }

    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ForbiddenException("User not found: " + userId));

    if (allowedRoles.length > 0) {
      boolean roleAllowed = Arrays.asList(allowedRoles).contains(user.getRole());
      if (!roleAllowed) {
        throw new ForbiddenException("User role " + user.getRole() + " is not authorized");
      }
    }

    return user;
  }
}
