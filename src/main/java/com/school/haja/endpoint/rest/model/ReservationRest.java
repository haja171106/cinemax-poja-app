package com.school.haja.endpoint.rest.model;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRest {
  private UUID id;
  private Instant createdAt;
  private UUID userId;
  private UUID projectionId;
  private UUID seatId;
}
