package com.school.haja.repository.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "projection")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Projection {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private Instant datetime;

  @Column(name = "seat_price", nullable = false, precision = 10, scale = 2)
  private BigDecimal seatPrice;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "movie_id", nullable = false)
  private Movie movie;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_id", nullable = false)
  private Room room;

  @OneToMany(mappedBy = "projection", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Reservation> reservations;
}
