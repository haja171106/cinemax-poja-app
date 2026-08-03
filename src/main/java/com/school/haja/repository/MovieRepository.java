package com.school.haja.repository;

import com.school.haja.repository.model.Genre;
import com.school.haja.repository.model.Movie;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {
  List<Movie> findByGenre(Genre genre);
}
