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
  void create_and_find_movie_by_genre_ok() {
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

    movieRepository.save(movie1);
    movieRepository.save(movie2);

    List<Movie> sciFiMovies = movieRepository.findByGenre(Genre.SCI_FI);
    assertTrue(sciFiMovies.size() >= 2);
    assertTrue(sciFiMovies.stream().anyMatch(m -> m.getTitle().equals("Inception")));
    assertTrue(sciFiMovies.stream().anyMatch(m -> m.getTitle().equals("Interstellar")));
  }
}
