package com.example.hospitalsystem.Respo;

import com.example.hospitalsystem.ettities.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PatientRespo extends MongoRepository<Patient,String> {
    boolean existsByUserId(String userId);
    List<Patient> findByDoctorId(String doctorId);
}
