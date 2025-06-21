package com.example.hospitalsystem.services;

import com.example.hospitalsystem.ettities.Patient;
import com.example.hospitalsystem.ettities.doctorpojo;
import com.example.hospitalsystem.Respo.PatientRespo;
import com.example.hospitalsystem.Respo.DoctorRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    @Autowired
    private final PatientRespo patientRepository;
    @Autowired
    private DoctorRespository doctorRepository;
    @Autowired
    private EmailService emailService;

    public DoctorService(PatientRespo patientRepository) {
        this.patientRepository = patientRepository;
    }
    public List<Patient> getPatientsByDoctorId(String doctorId) {
        return patientRepository.findByDoctorId(doctorId);
    }



    public doctorpojo createDoctor(doctorpojo doctor) {
        doctorpojo savedDoctor = doctorRepository.save(doctor);
        emailService.sendWelcomeEmail(savedDoctor.getEmail(), savedDoctor.getName());
        return savedDoctor;
    }

    public List<doctorpojo> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Optional<doctorpojo> getDoctorById(String id) {
        return doctorRepository.findById(id);
    }

    public doctorpojo updateDoctor(String id, doctorpojo updatedDoctor) {
        Optional<doctorpojo> optionalDoctor = doctorRepository.findById(id);
        if (optionalDoctor.isPresent()) {
            doctorpojo doctor = optionalDoctor.get();
            if (updatedDoctor.getName() != null) {
                doctor.setName(updatedDoctor.getName());
            }
            if (updatedDoctor.getSpecialization() != null) {
                doctor.setSpecialization(updatedDoctor.getSpecialization());
            }
            if (updatedDoctor.getEmail() != null) {
                doctor.setEmail(updatedDoctor.getEmail());
            }
            if (updatedDoctor.getPhone() != null) {
                doctor.setPhone(updatedDoctor.getPhone());
            }
            if (updatedDoctor.getExperience() > 0) {
                doctor.setExperience(updatedDoctor.getExperience());
            }
            if (updatedDoctor.isAvailable() != doctor.isAvailable()) {
                doctor.setAvailable(updatedDoctor.isAvailable());
            }

            return doctorRepository.save(doctor);
        } else {
            return null;
        }
    }

    public boolean deleteDoctor(String id) {
        if (doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    // Find doctor by email
    public doctorpojo findByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }
}
