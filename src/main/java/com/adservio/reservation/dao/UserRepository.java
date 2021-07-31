package com.adservio.reservation.dao;

import com.adservio.reservation.entities.Role;
import com.adservio.reservation.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
     User findByUsername(String username);
     List<User> findByRoles_Id(Long id);
}