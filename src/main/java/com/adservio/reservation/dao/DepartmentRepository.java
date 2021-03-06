package com.adservio.reservation.dao;

import com.adservio.reservation.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByName(String name);
}