package com.hostel.complaintbox.repository;

import com.hostel.complaintbox.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResidentRepository extends JpaRepository<Resident, Integer> {
}