package com.adservio.reservation.entities;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity

@Data
@AllArgsConstructor
@NoArgsConstructor


public class Booking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    Date StartDate;
    Date EndDate;
    private String description;
    private String code;

    @OneToOne
    private User user;
    @ManyToOne
    private Room room;







}
