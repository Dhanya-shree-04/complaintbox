package com.hostel.complaintbox.repository;

import com.hostel.complaintbox.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

    List<Complaint> findByResidentId(Integer residentId);

    List<Complaint> findAllByOrderByCreatedAtAsc();
}