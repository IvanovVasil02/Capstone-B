package com.ivanovvasil.CapstoneB.doctor;

import com.ivanovvasil.CapstoneB.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DoctorsService {
  @Autowired
  DoctorsRepo dr;

  public Doctor save(Doctor doctor) {
    dr.save(doctor);
    return null;
  }

  public Doctor findById(UUID id) {
    return dr.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public Doctor findByIdAndUpdate(UUID id, DoctorUpdateDTO body) {
    Doctor doctor = this.findById(id);
    doctor.setEmail(body.email());
    doctor.setEmergencyContact(body.emergencyContact());
    doctor.setPhoneNumber(body.phoneNumber());
    return dr.save(doctor);
  }

  public List<Doctor> getAll() {
    return dr.findAll();
  }
}
