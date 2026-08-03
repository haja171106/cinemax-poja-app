package com.school.haja.repository;

import com.school.haja.repository.model.Room;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
  Optional<Room> findByNumber(String number);
}
