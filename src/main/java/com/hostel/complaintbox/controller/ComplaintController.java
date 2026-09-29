package com.hostel.complaintbox.controller;

import com.hostel.complaintbox.entity.Complaint;
import com.hostel.complaintbox.service.ComplaintService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complaints")
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping
    public Complaint createComplaint(@RequestBody Complaint complaint) {
        return complaintService.createComplaint(complaint);
    }

    @GetMapping
    public List<Complaint> getAllComplaints() {
        return complaintService.getAllComplaints();
    }
    @GetMapping("/resident/{residentId}")
    public List<Complaint> getComplaintsByResident(
            @PathVariable Integer residentId) {

        return complaintService.getComplaintsByResident(residentId);
    }
    @GetMapping("/warden")
    public List<Complaint> getComplaintsForWarden() {
        return complaintService.getComplaintsOldestFirst();
    }

    @GetMapping("/{id}")
    public Complaint getComplaintById(@PathVariable Integer id) {
        return complaintService.getComplaintById(id);
    }

    @PutMapping("/{id}")
    public Complaint updateComplaint(
            @PathVariable Integer id,
            @RequestParam Integer staffId,
            @RequestBody Complaint complaint) {

        Complaint existing = complaintService.getComplaintById(id);

        existing.setRoomNumber(complaint.getRoomNumber());
        existing.setDescription(complaint.getDescription());
        existing.setRemark(complaint.getRemark());
        existing.setAssignedStaff(complaint.getAssignedStaff());

        return complaintService.updateComplaint(
                existing,
                complaint.getStatus(),
                staffId
        );
    }

    @DeleteMapping("/{id}")
    public String deleteComplaint(@PathVariable Integer id) {
        complaintService.deleteComplaint(id);
        return "Complaint deleted successfully";
    }
}