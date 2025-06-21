package com.example.hospitalsystem.ettities;

import com.example.hospitalsystem.ettities.AuditBaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "patients")
public class Patient extends AuditBaseEntity {

    @Id
    private String id;

    @NotBlank(message = "User ID is required")
    private String userId;
    @NotBlank(message = "name  is required")
    private String name;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Invalid phone number format")
    private String phone;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female, or Other")
    private String gender;

    @Min(value = 0, message = "Age must be a positive number")
    private int age;
    @NotBlank(message = "id  is required")
    private String doctorId;
}
