package com.adservio.reservation.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor

@Table(name = "reservation")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reservation_id")
    private Long id;
    Date StartDate;
    Date EndDate;
    private Long duration;
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;



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
