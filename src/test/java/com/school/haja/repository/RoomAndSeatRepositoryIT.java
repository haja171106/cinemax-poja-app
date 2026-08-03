package com.school.haja.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.school.haja.conf.FacadeIT;
import com.school.haja.repository.model.Room;
import com.school.haja.repository.model.Seat;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

class RoomAndSeatRepositoryIT extends FacadeIT {

  @Autowired private RoomRepository roomRepository;
  @Autowired private SeatRepository seatRepository;

  @Test
  void create_room_and_seats_ok() {
    String roomNum = "Salle-" + System.currentTimeMillis();
    Room room = Room.builder().number(roomNum).capacity(50).build();
    Room savedRoom = roomRepository.save(room);
    assertNotNull(savedRoom.getId());

    Seat seatA1 = Seat.builder().number("A1").room(savedRoom).build();
    Seat seatA2 = Seat.builder().number("A2").room(savedRoom).build();
    seatRepository.save(seatA1);
    seatRepository.save(seatA2);

    Optional<Room> foundRoom = roomRepository.findByNumber(roomNum);
    assertTrue(foundRoom.isPresent());
    assertEquals(50, foundRoom.get().getCapacity());

    List<Seat> seatsInRoom = seatRepository.findByRoomId(savedRoom.getId());
    assertEquals(2, seatsInRoom.size());
  }

  @Test
  void create_room_with_duplicate_number_fails() {
    String duplicateNum = "Room-DUP-" + System.currentTimeMillis();
    Room room1 = Room.builder().number(duplicateNum).capacity(30).build();
    roomRepository.saveAndFlush(room1);

    Room room2 = Room.builder().number(duplicateNum).capacity(40).build();
    assertThrows(
        DataIntegrityViolationException.class,
        () -> {
          roomRepository.saveAndFlush(room2);
        });
  }
}
