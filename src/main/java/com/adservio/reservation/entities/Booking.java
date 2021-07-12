package com.adservio.reservation.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor

@Table(name = "reservation")
public class Booking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    Date StartDate;
    Date EndDate;
    private Long duration;
    private String description;
    private String reservationCode;

    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;






    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Booking booking = (Booking) o;

        return Objects.equals(id, booking.id);
    }

    @Override
    public int hashCode() {
        return 1568749648;
    }
}
