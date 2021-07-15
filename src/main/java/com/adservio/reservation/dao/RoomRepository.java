package com.adservio.reservation.dao;

import com.adservio.reservation.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findByName(String name);
}