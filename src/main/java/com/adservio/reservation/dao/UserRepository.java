package com.adservio.reservation.dao;

import com.adservio.reservation.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
     User findByUsername(String username);
}