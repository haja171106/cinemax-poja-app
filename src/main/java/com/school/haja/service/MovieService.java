package com.school.haja.service;

import com.school.haja.repository.MovieRepository;
import com.school.haja.repository.model.Movie;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovieService {

  private final MovieRepository movieRepository;

  public List<Movie> saveAll(List<Movie> movies) {
    return movieRepository.saveAll(movies);
  }
}
