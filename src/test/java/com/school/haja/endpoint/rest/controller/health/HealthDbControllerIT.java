package com.school.haja.endpoint.rest.controller.health;

import static com.school.haja.endpoint.rest.controller.health.PingController.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.school.haja.PojaGenerated;
import com.school.haja.conf.FacadeIT;
import com.school.haja.repository.DummyRepository;
import com.school.haja.repository.model.Dummy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@PojaGenerated
class HealthDbControllerIT extends FacadeIT {

  @Autowired HealthDbController healthDbController;
  @Autowired DummyRepository dummyRepository;

  @Test
  void health_db_ok() {
    if (dummyRepository.findAll().isEmpty()) {
      Dummy dummy = new Dummy();
      dummy.setId("dummy-id");
      dummyRepository.save(dummy);
    }

    var response = healthDbController.dummyTable_should_not_be_empty();
    assertNotNull(response);
    assertEquals(OK, response);
  }
}
