package com.adservio.reservation.entities;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Booking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime StartDate;
    private LocalDateTime EndDate;
    private String description;
    @Column(unique = true)
    private String code;

    @JsonIgnore
    @ManyToOne
    @JoinTable( name = "users_reservations",
    joinColumns = @JoinColumn(name = "booking_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User user;


    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;







}
