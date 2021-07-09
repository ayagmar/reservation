package com.adservio.reservation.dao;

import com.adservio.reservation.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource

public interface RoleRepository extends JpaRepository<Role, Long> {
}