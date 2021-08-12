package com.adservio.reservation.service;

import com.adservio.reservation.dao.BookingRepository;
import com.adservio.reservation.dao.RoomRepository;
import com.adservio.reservation.entities.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ScheduleService {
    private final BookingRepository bookingRepository;
    private final EmailSenderService emailSenderService;
    private final RoomRepository roomRepository;

    @Scheduled(cron = "0 0/10 * * * *")
    public void job1() throws InterruptedException {

        LocalDateTime currentDate = LocalDateTime.now();
        List<Booking> bookingList = bookingRepository.findAll();
        List<Booking> activeBookings = new ArrayList<>();

        for (Booking booking : bookingList) {
            if (booking.getEndDate().plusMinutes(5).isAfter(currentDate))
                activeBookings.add(booking);
        }

        for (Booking activeBooking : activeBookings) {

            if (activeBooking.getEndDate().truncatedTo(ChronoUnit.SECONDS).isEqual(currentDate.truncatedTo(ChronoUnit.SECONDS))) {
                String body = "your room " + activeBooking.getRoom().getName() + " is now free, booking " + activeBooking.getCode() + " has ended ";
                String to = activeBooking.getUser().getEmail();
                String subject = "Reservation completed";
                try {
                    emailSenderService.SendEmail(to, body, subject);
                } catch (MailException mailException) {
                    mailException.printStackTrace();
                }
                activeBooking.getRoom().setReserved(false);
                roomRepository.save(activeBooking.getRoom());
            }

        }
        System.out.println("Ran Job at " + LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

}
