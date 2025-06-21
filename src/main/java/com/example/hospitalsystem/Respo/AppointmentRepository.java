package com.example.hospitalsystem.Respo;

import com.example.hospitalsystem.ettities.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {
    List<Appointment> findByDoctorId(String doctorId);
    List<Appointment> findByPatientId(String patientId);
    List<Appointment> findByDoctorIdAndAppointmentDate(String doctorId, LocalDateTime appointmentDate);


    List<Appointment> findByDoctorIdAndAppointmentDateBetween(String doctorId, LocalDateTime start, LocalDateTime end);
}
