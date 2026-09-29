package com.hostel.complaintbox.service;

import com.hostel.complaintbox.entity.Complaint;
import com.hostel.complaintbox.repository.ComplaintRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    public Complaint createComplaint(Complaint complaint) {
        complaint.setStatus("OPEN");

        complaint.setCreatedAt(java.time.LocalDateTime.now());
        complaint.setUpdatedAt(java.time.LocalDateTime.now());

        return complaintRepository.save(complaint);
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }
    public List<Complaint> getComplaintsByResident(Integer residentId) {
        return complaintRepository.findByResidentId(residentId);
    }
    public List<Complaint> getComplaintsOldestFirst() {
        return complaintRepository.findAllByOrderByCreatedAtAsc();
    }

    public Complaint getComplaintById(Integer id) {
        return complaintRepository.findById(id)
                .orElseThrow(() ->
                        new com.hostel.complaintbox.exception.ComplaintNotFoundException(
                                "Complaint with ID " + id + " not found"
                        )
                );
    }

    public Complaint updateComplaint(Complaint complaint, String newStatus, Integer staffId) {

        Complaint existing = complaintRepository.findById(complaint.getId())
                .orElseThrow(() ->
                        new com.hostel.complaintbox.exception.ComplaintNotFoundException(
                                "Complaint with ID " + complaint.getId() + " not found"
                        ));

        String currentStatus = existing.getStatus();
        System.out.println("DEBUG staffId = " + staffId);
        System.out.println("DEBUG newStatus = " + newStatus);
        if (newStatus.equals("RESOLVED")) {

            boolean isWarden = staffId.equals(2);

            boolean isAssignedStaff =
                    existing.getAssignedStaff() != null &&
                            existing.getAssignedStaff().getId().equals(staffId);

            if (!isAssignedStaff && !isWarden) {
                throw new RuntimeException(
                        "Only assigned staff or Warden can resolve the complaint"
                );
            }
        }

        if (currentStatus.equals("OPEN") &&
                !newStatus.equals("IN_PROGRESS")) {

            throw new RuntimeException(
                    "OPEN complaint can only move to IN_PROGRESS"
            );
        }

        if (currentStatus.equals("IN_PROGRESS") &&
                !newStatus.equals("RESOLVED")) {

            throw new RuntimeException(
                    "IN_PROGRESS complaint can only move to RESOLVED"
            );
        }

        existing.setRoomNumber(complaint.getRoomNumber());
        existing.setDescription(complaint.getDescription());
        existing.setRemark(complaint.getRemark());
        existing.setStatus(newStatus);
        existing.setUpdatedAt(java.time.LocalDateTime.now());

        return complaintRepository.save(existing);
    }

    public void deleteComplaint(Integer id) {
        complaintRepository.deleteById(id);
    }
}