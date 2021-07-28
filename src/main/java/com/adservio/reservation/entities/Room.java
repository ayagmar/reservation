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
    @Column(nullable = false)
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "room",cascade = CascadeType.ALL)
    private Collection<Booking> reservation;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Department department;


}
