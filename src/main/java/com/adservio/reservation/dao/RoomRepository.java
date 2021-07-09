package com.adservio.reservation.dao;

import com.adservio.reservation.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource

public interface RoomRepository extends JpaRepository<Room, Long> {
}