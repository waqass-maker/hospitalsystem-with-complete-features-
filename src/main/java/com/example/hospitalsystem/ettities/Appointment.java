package com.example.hospitalsystem.ettities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "appointments")
public class Appointment extends AuditBaseEntity {

    @Id
    private String id;

    private String doctorId;
    private String patientId;
    private LocalDateTime appointmentDate;
    private String status; // e.g., PENDING, CONFIRMED, CANCELLED
}
