package com.example.hospitalsystem.Controllers;

import com.example.hospitalsystem.ettities.Patient;
import com.example.hospitalsystem.ettities.doctorpojo;
import com.example.hospitalsystem.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doc")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/{doctorId}/patients")
    public ResponseEntity<List<Patient>> getPatientsForDoctor(@PathVariable String doctorId) {
        return ResponseEntity.ok(doctorService.getPatientsByDoctorId(doctorId));
    }
    // 🩺 Health Check
    @GetMapping("/health")
    public String healthCheck() {
        return "System is working fine";
    }

    // ➕ Create Doctor
    @PostMapping("/add")
    public ResponseEntity<doctorpojo> addDoctor(@RequestBody doctorpojo doctor) {
        doctorpojo savedDoctor = doctorService.createDoctor(doctor);
        return ResponseEntity.ok(savedDoctor);
    }

    @GetMapping("/all")
    public ResponseEntity<List<doctorpojo>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable String id) {
        Optional<doctorpojo> doctor = doctorService.getDoctorById(id);
        return doctor != null ? ResponseEntity.ok(doctor) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable String id, @RequestBody doctorpojo dto) {
        doctorpojo updated = doctorService.updateDoctor(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable String id) {
        boolean deleted = doctorService.deleteDoctor(id);
        return deleted ? ResponseEntity.ok("Doctor deleted") : ResponseEntity.notFound().build();
    }
}
