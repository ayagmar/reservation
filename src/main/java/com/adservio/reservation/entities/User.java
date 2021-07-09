package com.adservio.reservation.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.awt.print.Book;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @Column(name="user_id")
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
    boolean reserving=false;


/*
@OneToMany(cascade = CascadeType.ALL)
@ToString.Exclude
private List<Booking> reservations=new ArrayList<>();
*/



    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;

        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return 562048007;
    }
}
