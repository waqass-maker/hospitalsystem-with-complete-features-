package com.example.hospitalsystem.services;

import com.example.hospitalsystem.ettities.Appointment;
import com.example.hospitalsystem.ettities.doctorpojo;
import com.example.hospitalsystem.Respo.AppointmentRepository;
import com.example.hospitalsystem.Respo.DoctorRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRespository doctorRepository;

    public Appointment createAppointment(Appointment appointment) {
        Optional<doctorpojo> optionalDoctor = doctorRepository.findById(appointment.getDoctorId());

        if (optionalDoctor.isEmpty()) {
            throw new RuntimeException("Doctor not found");
        }

        doctorpojo doctor = optionalDoctor.get();
        LocalTime appointmentTime = appointment.getAppointmentDate().toLocalTime();

        if (appointmentTime.isBefore(LocalTime.from(doctor.getAvailableStartTime())) || appointmentTime.isAfter(LocalTime.from(doctor.getAvailableEndTime()))) {
            throw new RuntimeException("Doctor not available at this time. Available from "
                    + doctor.getAvailableStartTime() + " to " + doctor.getAvailableEndTime());
        }


        List<Appointment> existingAppointments = appointmentRepository.findByDoctorIdAndAppointmentDate(
                appointment.getDoctorId(), appointment.getAppointmentDate());

        if (!existingAppointments.isEmpty()) {
            throw new RuntimeException("Appointment already exists at this time. Please choose another time slot.");
        }

        appointment.setStatus("CONFIRMED");
        return appointmentRepository.save(appointment);
    }

    public List<LocalTime> getAvailableTimeSlots(String doctorId, LocalDateTime date) {
        Optional<doctorpojo> doctorOpt = doctorRepository.findById(doctorId);
        if (doctorOpt.isEmpty()) {
            throw new RuntimeException("Doctor not found");
        }

        doctorpojo doctor = doctorOpt.get();
        List<Appointment> bookedAppointments = appointmentRepository.findByDoctorIdAndAppointmentDateBetween(
                doctorId,
                date.toLocalDate().atStartOfDay(),
                date.toLocalDate().atTime(23, 59)
        );


        List<LocalTime> bookedTimes = bookedAppointments.stream()
                .map(a -> a.getAppointmentDate().toLocalTime())
                .collect(Collectors.toList());

        LocalTime start = doctor.getAvailableStartTime().toLocalTime();
        LocalTime end = doctor.getAvailableEndTime().toLocalTime();


        List<LocalTime> availableSlots = new java.util.ArrayList<>();
        while (start.isBefore(end)) {
            if (!bookedTimes.contains(start)) {
                availableSlots.add(start);
            }
            start = start.plusMinutes(30);
        }

        return availableSlots;
    }
}
