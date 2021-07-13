package com.adservio.reservation.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "room")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomStatus roomstate;
    @OneToMany(mappedBy = "room",cascade = CascadeType.ALL)
    @ToString.Exclude
    private Collection<Booking> reservation;


    @OneToOne(cascade = CascadeType.PERSIST)
    private Department department;






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
