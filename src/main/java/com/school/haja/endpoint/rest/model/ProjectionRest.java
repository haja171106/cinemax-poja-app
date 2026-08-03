package com.school.haja.endpoint.rest.model;

import java.math.BigDecimal;
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
public class ProjectionRest {
  private UUID id;
  private Instant datetime;
  private BigDecimal seatPrice;
  private UUID movieId;
  private UUID roomId;
}
