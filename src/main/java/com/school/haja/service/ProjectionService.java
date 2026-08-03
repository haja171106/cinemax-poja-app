package com.school.haja.service;

import com.school.haja.repository.ProjectionRepository;
import com.school.haja.repository.model.Projection;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectionService {

  private final ProjectionRepository projectionRepository;

  public List<Projection> getProjections() {
    return projectionRepository.findAll();
  }

  public List<Projection> saveAll(List<Projection> projections) {
    return projectionRepository.saveAll(projections);
  }
}
