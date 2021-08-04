package com.adservio.reservation.dao;

import com.adservio.reservation.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByUsername(String username);

    List<User> findByRoles_Id(Long id);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}


