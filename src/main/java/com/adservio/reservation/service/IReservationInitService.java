package com.adservio.reservation.service;

import com.adservio.reservation.entities.Room;

public interface IReservationInitService {
    void initRooms();
    void initUser();
    void initReservation();
    Room addroom(Room r);



}
