package com.school.haja.endpoint.rest.mapper;

import com.school.haja.endpoint.rest.model.MovieRest;
import com.school.haja.repository.model.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

  public MovieRest toRest(Movie domain) {
    if (domain == null) {
      return null;
    }
    return MovieRest.builder()
        .id(domain.getId())
        .title(domain.getTitle())
        .genre(domain.getGenre())
        .description(domain.getDescription())
        .duration(domain.getDuration())
        .build();
  }

  public Movie toDomain(MovieRest rest) {
    if (rest == null) {
      return null;
    }
    return Movie.builder()
        .id(rest.getId())
        .title(rest.getTitle())
        .genre(rest.getGenre())
        .description(rest.getDescription())
        .duration(rest.getDuration())
        .build();
  }
}
