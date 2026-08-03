package com.school.haja.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.school.haja.conf.FacadeIT;
import com.school.haja.repository.model.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

class ReservationRepositoryIT extends FacadeIT {

  @Autowired private ReservationRepository reservationRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private MovieRepository movieRepository;
  @Autowired private RoomRepository roomRepository;
  @Autowired private SeatRepository seatRepository;
  @Autowired private ProjectionRepository projectionRepository;

  @Test
  void create_reservation_and_prevent_double_booking_ok() {
    User user1 =
        userRepository.save(
            User.builder()
                .firstName("User1")
                .lastName("Test")
                .birthdate(LocalDate.of(1990, 1, 1))
                .email("user1." + System.currentTimeMillis() + "@test.com")
                .password("pass")
                .role(UserRole.CLIENT)
                .build());

    User user2 =
        userRepository.save(
            User.builder()
                .firstName("User2")
                .lastName("Test")
                .birthdate(LocalDate.of(1992, 2, 2))
                .email("user2." + System.currentTimeMillis() + "@test.com")
                .password("pass")
                .role(UserRole.CLIENT)
                .build());

    Movie movie =
        movieRepository.save(
            Movie.builder()
                .title("The Matrix")
                .genre(Genre.SCI_FI)
                .duration(Duration.ofMinutes(136))
                .build());

    Room room =
        roomRepository.save(
            Room.builder().number("Room-Resa-" + System.currentTimeMillis()).capacity(20).build());

    Seat seat1 = seatRepository.save(Seat.builder().number("B10").room(room).build());

    Projection projection =
        projectionRepository.save(
            Projection.builder()
                .movie(movie)
                .room(room)
                .datetime(Instant.now().plusSeconds(7200))
                .seatPrice(new BigDecimal("10.00"))
                .build());

    Reservation res1 =
        Reservation.builder()
            .createdAt(Instant.now())
            .user(user1)
            .projection(projection)
            .seat(seat1)
            .build();

    Reservation savedRes1 = reservationRepository.saveAndFlush(res1);
    assertNotNull(savedRes1.getId());

    List<Reservation> user1Reservations =
        reservationRepository.findAllByUserId(user1.getId());
    assertEquals(1, user1Reservations.size());

    Reservation res2 =
        Reservation.builder()
            .createdAt(Instant.now())
            .user(user2)
            .projection(projection)
            .seat(seat1)
            .build();

    assertThrows(
        DataIntegrityViolationException.class,
        () -> {
          reservationRepository.saveAndFlush(res2);
        });
  }
}
