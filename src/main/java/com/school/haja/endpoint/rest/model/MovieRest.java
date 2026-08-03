package com.school.haja.endpoint.rest.model;

import com.school.haja.repository.model.Genre;
import java.time.Duration;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieRest {
  private UUID id;
  private String title;
  private Genre genre;
  private String description;
  private Duration duration;
}
