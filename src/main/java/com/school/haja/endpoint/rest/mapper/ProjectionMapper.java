package com.school.haja.endpoint.rest.mapper;

import com.school.haja.endpoint.rest.controller.exception.NotFoundException;
import com.school.haja.endpoint.rest.model.ProjectionRest;
import com.school.haja.repository.MovieRepository;
import com.school.haja.repository.RoomRepository;
import com.school.haja.repository.model.Movie;
import com.school.haja.repository.model.Projection;
import com.school.haja.repository.model.Room;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProjectionMapper {

  private final MovieRepository movieRepository;
  private final RoomRepository roomRepository;

  public ProjectionRest toRest(Projection domain) {
    if (domain == null) {
      return null;
    }
    return ProjectionRest.builder()
        .id(domain.getId())
        .datetime(domain.getDatetime())
        .seatPrice(domain.getSeatPrice())
        .movieId(domain.getMovie() != null ? domain.getMovie().getId() : null)
        .roomId(domain.getRoom() != null ? domain.getRoom().getId() : null)
        .build();
  }

  public Projection toDomain(ProjectionRest rest) {
    if (rest == null) {
      return null;
    }
    Movie movie = null;
    if (rest.getMovieId() != null) {
      movie =
          movieRepository
              .findById(rest.getMovieId())
              .orElseThrow(() -> new NotFoundException("Movie not found: " + rest.getMovieId()));
    }
    Room room = null;
    if (rest.getRoomId() != null) {
      room =
          roomRepository
              .findById(rest.getRoomId())
              .orElseThrow(() -> new NotFoundException("Room not found: " + rest.getRoomId()));
    }
    return Projection.builder()
        .id(rest.getId())
        .datetime(rest.getDatetime())
        .seatPrice(rest.getSeatPrice())
        .movie(movie)
        .room(room)
        .build();
  }
}
