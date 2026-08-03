package com.school.haja.endpoint.rest.controller;

import com.school.haja.endpoint.rest.mapper.ProjectionMapper;
import com.school.haja.endpoint.rest.model.ProjectionRest;
import com.school.haja.repository.model.Projection;
import com.school.haja.service.ProjectionService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ProjectionController {

  private final ProjectionService projectionService;
  private final ProjectionMapper projectionMapper;

  @GetMapping("/projections")
  public List<ProjectionRest> getProjections() {
    List<Projection> projections = projectionService.getProjections();
    return projections.stream().map(projectionMapper::toRest).toList();
  }

  @PutMapping("/projections")
  public List<ProjectionRest> createOrUpdateProjections(@RequestBody List<ProjectionRest> toSave) {
    List<Projection> domainProjections = toSave.stream().map(projectionMapper::toDomain).toList();
    List<Projection> savedProjections = projectionService.saveAll(domainProjections);
    return savedProjections.stream().map(projectionMapper::toRest).toList();
  }
}
