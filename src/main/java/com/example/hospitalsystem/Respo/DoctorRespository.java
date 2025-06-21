package com.example.hospitalsystem.Respo;

import com.example.hospitalsystem.ettities.doctorpojo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DoctorRespository extends MongoRepository<doctorpojo,String> {

    doctorpojo findByEmail(String email);
}
