package com.school.haja.endpoint.rest.controller.health;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.school.haja.PojaGenerated;
import com.school.haja.conf.FacadeIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@PojaGenerated
class PingControllerIT extends FacadeIT {

  @Autowired PingController pingController;

  @Test
  void ping() {
    assertEquals("pong", pingController.ping());
  }
}
