package com.adservio.reservation.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomStatus roomstate;
    @JsonIgnore
    @OneToMany(mappedBy = "room",cascade = CascadeType.ALL)
    @ToString.Exclude
    private Collection<Booking> reservation;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Department department;


}
