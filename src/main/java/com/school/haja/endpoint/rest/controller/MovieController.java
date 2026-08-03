package com.school.haja.endpoint.rest.controller;

import com.school.haja.endpoint.rest.mapper.MovieMapper;
import com.school.haja.endpoint.rest.model.MovieRest;
import com.school.haja.repository.model.Movie;
import com.school.haja.service.MovieService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MovieController {

  private final MovieService movieService;
  private final MovieMapper movieMapper;

  @PutMapping("/movies")
  public List<MovieRest> createOrUpdateMovies(@RequestBody List<MovieRest> toSave) {
    List<Movie> domainMovies = toSave.stream().map(movieMapper::toDomain).toList();
    List<Movie> savedMovies = movieService.saveAll(domainMovies);
    return savedMovies.stream().map(movieMapper::toRest).toList();
  }
}
