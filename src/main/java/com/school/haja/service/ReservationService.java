package com.school.haja.service;

import com.school.haja.endpoint.rest.controller.exception.ForbiddenException;
import com.school.haja.endpoint.rest.controller.exception.NotFoundException;
import com.school.haja.repository.ReservationRepository;
import com.school.haja.repository.model.Reservation;
import com.school.haja.repository.model.User;
import com.school.haja.repository.model.UserRole;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;

  public List<Reservation> getReservations() {
    return reservationRepository.findAll();
  }

  public Reservation getReservationById(UUID id, User requester) {
    Reservation reservation =
        reservationRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Reservation not found: " + id));

    if (requester.getRole() == UserRole.CLIENT) {
      if (reservation.getUser() == null
          || !reservation.getUser().getId().equals(requester.getId())) {
        throw new ForbiddenException("Client does not own this reservation");
      }
    }
    return reservation;
  }

  public List<Reservation> saveAll(List<Reservation> reservations) {
    return reservationRepository.saveAll(reservations);
  }
}
