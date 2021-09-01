package com.adservio.reservation.utilClass;

import lombok.Data;


public class FormClass {
    @Data
    public static class UserBookingForm {
        private String dateStart;
        private String dateEnd;
        private String description;
        private String roomName;
    }

    @Data
    public static class BookingUpdate {
        private String dateStart;
        private String dateEnd;
        private String description;
    }

    @Data
    public static class DateForm {
        String start;
        String end;
    }

    @Data
    public static class RoomForm {
        String name;
        Long id;
    }

}
