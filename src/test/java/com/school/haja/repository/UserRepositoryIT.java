package com.school.haja.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.school.haja.conf.FacadeIT;
import com.school.haja.repository.model.User;
import com.school.haja.repository.model.UserRole;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

class UserRepositoryIT extends FacadeIT {

  @Autowired private UserRepository userRepository;

  @Test
  void create_and_find_user_by_email_ok() {
    String email = "john.doe." + System.currentTimeMillis() + "@gmail.com";
    User user =
        User.builder()
            .firstName("John")
            .lastName("Doe")
            .birthdate(LocalDate.of(1995, 5, 15))
            .email(email)
            .password("securePassword123")
            .phone("+33612345678")
            .role(UserRole.CLIENT)
            .build();

    User savedUser = userRepository.save(user);
    assertNotNull(savedUser.getId());

    Optional<User> foundUser = userRepository.findByEmail(email);
    assertTrue(foundUser.isPresent());
    assertEquals("John", foundUser.get().getFirstName());
    assertEquals("Doe", foundUser.get().getLastName());
    assertEquals(UserRole.CLIENT, foundUser.get().getRole());
  }

  @Test
  void create_user_with_duplicate_email_fails() {
    String uniqueEmail = "duplicate." + System.currentTimeMillis() + "@gmail.com";
    User user1 =
        User.builder()
            .firstName("Alice")
            .lastName("Smith")
            .birthdate(LocalDate.of(2000, 1, 1))
            .email(uniqueEmail)
            .password("password")
            .role(UserRole.MANAGER)
            .build();

    userRepository.saveAndFlush(user1);

    User user2 =
        User.builder()
            .firstName("Bob")
            .lastName("Smith")
            .birthdate(LocalDate.of(1998, 2, 2))
            .email(uniqueEmail)
            .password("password2")
            .role(UserRole.CLIENT)
            .build();

    assertThrows(
        DataIntegrityViolationException.class,
        () -> {
          userRepository.saveAndFlush(user2);
        });
  }
}
