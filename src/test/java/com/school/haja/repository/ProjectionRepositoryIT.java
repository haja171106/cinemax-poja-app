package com.school.haja.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.school.haja.conf.FacadeIT;
import com.school.haja.repository.model.Genre;
import com.school.haja.repository.model.Movie;
import com.school.haja.repository.model.Projection;
import com.school.haja.repository.model.Room;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ProjectionRepositoryIT extends FacadeIT {

  @Autowired private ProjectionRepository projectionRepository;
  @Autowired private MovieRepository movieRepository;
  @Autowired private RoomRepository roomRepository;

  @Test
  void create_and_find_projections_by_movie_ok() {
    Movie movie =
        movieRepository.save(
            Movie.builder()
                .title("Avatar")
                .genre(Genre.FANTASY)
                .description("Pandora exploration")
                .duration(Duration.ofMinutes(162))
                .build());

    Room room =
        roomRepository.save(
            Room.builder().number("Room-Proj-" + System.currentTimeMillis()).capacity(100).build());

    Projection projection =
        Projection.builder()
            .movie(movie)
            .room(room)
            .datetime(Instant.now().plusSeconds(3600))
            .seatPrice(new BigDecimal("12.50"))
            .build();

    Projection savedProjection = projectionRepository.save(projection);
    assertNotNull(savedProjection.getId());

    List<Projection> movieProjections = projectionRepository.findByMovieId(movie.getId());
    assertEquals(1, movieProjections.size());
    assertEquals(new BigDecimal("12.50"), movieProjections.get(0).getSeatPrice());
    assertEquals(movie.getId(), movieProjections.get(0).getMovie().getId());
  }
}
