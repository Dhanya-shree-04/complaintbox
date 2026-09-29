package com.hostel.complaintbox.repository;

import com.hostel.complaintbox.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Integer> {
}