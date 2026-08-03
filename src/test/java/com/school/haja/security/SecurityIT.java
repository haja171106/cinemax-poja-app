package com.school.haja.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.school.haja.conf.FacadeIT;
import com.school.haja.repository.UserRepository;
import com.school.haja.repository.model.User;
import com.school.haja.repository.model.UserRole;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class SecurityIT extends FacadeIT {

  @Autowired private TestRestTemplate restTemplate;
  @Autowired private UserRepository userRepository;

  @Test
  void public_endpoint_ping_is_accessible_without_auth() {
    ResponseEntity<String> response = restTemplate.getForEntity("/ping", String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("pong", response.getBody());
  }

  @Test
  void public_endpoint_projections_is_accessible_without_auth() {
    ResponseEntity<String> response = restTemplate.getForEntity("/projections", String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void protected_endpoint_put_movies_without_header_returns_forbidden() {
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<List<Object>> entity = new HttpEntity<>(List.of(), headers);

    ResponseEntity<String> response =
        restTemplate.exchange("/movies", HttpMethod.PUT, entity, String.class);
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }

  @Test
  void protected_endpoint_put_movies_with_client_role_returns_forbidden() {
    User client =
        userRepository.save(
            User.builder()
                .firstName("ClientUser")
                .lastName("Test")
                .birthdate(LocalDate.of(2000, 1, 1))
                .email("client." + System.currentTimeMillis() + "@test.com")
                .password("pass")
                .role(UserRole.CLIENT)
                .build());

    HttpHeaders headers = new HttpHeaders();
    headers.set("X-User-Id", client.getId().toString());
    HttpEntity<List<Object>> entity = new HttpEntity<>(List.of(), headers);

    ResponseEntity<String> response =
        restTemplate.exchange("/movies", HttpMethod.PUT, entity, String.class);
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }

  @Test
  void protected_endpoint_put_movies_with_manager_role_returns_ok() {
    User manager =
        userRepository.save(
            User.builder()
                .firstName("ManagerUser")
                .lastName("Test")
                .birthdate(LocalDate.of(1985, 1, 1))
                .email("manager." + System.currentTimeMillis() + "@test.com")
                .password("pass")
                .role(UserRole.MANAGER)
                .build());

    HttpHeaders headers = new HttpHeaders();
    headers.set("X-User-Id", manager.getId().toString());
    headers.set("Content-Type", "application/json");
    HttpEntity<String> entity = new HttpEntity<>("[]", headers);

    ResponseEntity<String> response =
        restTemplate.exchange("/movies", HttpMethod.PUT, entity, String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
}
