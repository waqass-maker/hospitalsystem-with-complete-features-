package com.example.hospitalsystem.services;

import com.example.hospitalsystem.ettities.Patient;
import com.example.hospitalsystem.Respo.PatientRespo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRespo patientRepository;

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(String id) {
        return patientRepository.findById(id);
    }

    public Patient updatePatient(String id, Patient updatedPatient) {
        return patientRepository.findById(id).map(existing -> {
            if (StringUtils.hasText(updatedPatient.getAddress())) {
                existing.setAddress(updatedPatient.getAddress());
            }
            if (StringUtils.hasText(updatedPatient.getPhone())) {
                existing.setPhone(updatedPatient.getPhone());
            }
            if (StringUtils.hasText(updatedPatient.getGender())) {
                existing.setGender(updatedPatient.getGender());
            }
            if (updatedPatient.getAge() > 0) {
                existing.setAge(updatedPatient.getAge());
            }
            return patientRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
    }

    public void deletePatient(String id) {
        patientRepository.deleteById(id);
    }

    public boolean existsByUserId(String userId) {
        return patientRepository.existsByUserId(userId);
    }
}
