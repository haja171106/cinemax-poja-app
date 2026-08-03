package com.school.haja.endpoint.rest.mapper;

import com.school.haja.endpoint.rest.controller.exception.NotFoundException;
import com.school.haja.endpoint.rest.model.ReservationRest;
import com.school.haja.repository.ProjectionRepository;
import com.school.haja.repository.SeatRepository;
import com.school.haja.repository.UserRepository;
import com.school.haja.repository.model.Projection;
import com.school.haja.repository.model.Reservation;
import com.school.haja.repository.model.Seat;
import com.school.haja.repository.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReservationMapper {

  private final UserRepository userRepository;
  private final ProjectionRepository projectionRepository;
  private final SeatRepository seatRepository;

  public ReservationRest toRest(Reservation domain) {
    if (domain == null) {
      return null;
    }
    return ReservationRest.builder()
        .id(domain.getId())
        .createdAt(domain.getCreatedAt())
        .userId(domain.getUser() != null ? domain.getUser().getId() : null)
        .projectionId(domain.getProjection() != null ? domain.getProjection().getId() : null)
        .seatId(domain.getSeat() != null ? domain.getSeat().getId() : null)
        .build();
  }

  public Reservation toDomain(ReservationRest rest) {
    if (rest == null) {
      return null;
    }
    User user = null;
    if (rest.getUserId() != null) {
      user =
          userRepository
              .findById(rest.getUserId())
              .orElseThrow(() -> new NotFoundException("User not found: " + rest.getUserId()));
    }
    Projection projection = null;
    if (rest.getProjectionId() != null) {
      projection =
          projectionRepository
              .findById(rest.getProjectionId())
              .orElseThrow(
                  () -> new NotFoundException("Projection not found: " + rest.getProjectionId()));
    }
    Seat seat = null;
    if (rest.getSeatId() != null) {
      seat =
          seatRepository
              .findById(rest.getSeatId())
              .orElseThrow(() -> new NotFoundException("Seat not found: " + rest.getSeatId()));
    }

    return Reservation.builder()
        .id(rest.getId())
        .createdAt(rest.getCreatedAt())
        .user(user)
        .projection(projection)
        .seat(seat)
        .build();
  }
}
