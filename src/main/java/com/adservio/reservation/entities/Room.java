package com.adservio.reservation.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "room")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="room_id")
    private Long id;
    private String number;
    private Integer capacity;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomStatus roomstate;


    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Booking> reservation = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Room room = (Room) o;

        return Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return 1140760324;
    }
}
