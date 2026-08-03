package com.school.haja.repository.model;

import jakarta.persistence.*;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "movie")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String title;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Genre genre;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(nullable = false)
  private Duration duration;

  @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Projection> projections;
}
