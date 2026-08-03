package com.school.haja.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.school.haja.conf.FacadeIT;
import com.school.haja.repository.model.Genre;
import com.school.haja.repository.model.Movie;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MovieRepositoryIT extends FacadeIT {

  @Autowired private MovieRepository movieRepository;

  @Test
  void create_and_find_movies_ok() {
    Movie movie1 =
        Movie.builder()
            .title("Inception")
            .genre(Genre.SCI_FI)
            .description(
                "A thief who steals corporate secrets through the use of dream-sharing technology.")
            .duration(Duration.ofMinutes(148))
            .build();

    Movie movie2 =
        Movie.builder()
            .title("Interstellar")
            .genre(Genre.SCI_FI)
            .description(
                "A team of explorers travel through a wormhole in space in an attempt to ensure"
                    + " humanity's survival.")
            .duration(Duration.ofMinutes(169))
            .build();

    Movie savedMovie1 = movieRepository.save(movie1);
    Movie savedMovie2 = movieRepository.save(movie2);

    assertNotNull(savedMovie1.getId());
    assertNotNull(savedMovie2.getId());

    List<Movie> allMovies = movieRepository.findAll();
    assertTrue(allMovies.stream().anyMatch(m -> m.getTitle().equals("Inception")));
    assertTrue(allMovies.stream().anyMatch(m -> m.getTitle().equals("Interstellar")));
    assertTrue(
        allMovies.stream()
            .filter(m -> m.getTitle().equals("Inception"))
            .allMatch(m -> m.getGenre() == Genre.SCI_FI));
  }
}
