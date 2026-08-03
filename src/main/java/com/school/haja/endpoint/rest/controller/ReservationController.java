package com.school.haja.endpoint.rest.controller;

import com.school.haja.endpoint.rest.mapper.ReservationMapper;
import com.school.haja.endpoint.rest.model.ReservationRest;
import com.school.haja.repository.model.Reservation;
import com.school.haja.repository.model.User;
import com.school.haja.service.ReservationService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;
  private final ReservationMapper reservationMapper;

  @GetMapping("/reservations")
  public List<ReservationRest> getReservations() {
    List<Reservation> reservations = reservationService.getReservations();
    return reservations.stream().map(reservationMapper::toRest).toList();
  }

  @GetMapping("/reservations/{id}")
  public ReservationRest getReservationById(@PathVariable(name = "id") UUID id) {
    User requester = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Reservation reservation = reservationService.getReservationById(id, requester);
    return reservationMapper.toRest(reservation);
  }

  @PutMapping("/reservations")
  public List<ReservationRest> createOrUpdateReservations(
      @RequestBody List<ReservationRest> toSave) {
    List<Reservation> domainReservations =
        toSave.stream().map(reservationMapper::toDomain).toList();
    List<Reservation> savedReservations = reservationService.saveAll(domainReservations);
    return savedReservations.stream().map(reservationMapper::toRest).toList();
  }
}
