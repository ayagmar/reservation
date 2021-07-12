package com.adservio.reservation.dao;

import com.adservio.reservation.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


public interface UserRepository extends JpaRepository<User, Long> {

}