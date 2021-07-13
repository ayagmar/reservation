package com.adservio.reservation.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 40)
    private String FirstName;
    @Column(nullable = false,length = 40)
    private String LastName;
    @Column(unique = true,length = 40,nullable = false)
    private String email;
    @Column(nullable = false,length = 40)
    private String Password;



    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Collection<Booking> reservation;




}
