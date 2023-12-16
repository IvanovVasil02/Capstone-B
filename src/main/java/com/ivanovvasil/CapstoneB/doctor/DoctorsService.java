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

  public DoctorDTO convertToDoctorDTO(Doctor doctor) {
    return DoctorDTO.builder()
            .name(doctor.getName())
            .surname(doctor.getSurname())
            .fiscalCode(doctor.getFiscalCode())
            .doctorId(doctor.getId())
            .build();
  }

  public DoctorProfileDTO convertToDoctorProfileDTO(Doctor doctor) {
    return DoctorProfileDTO.builder()
            .name(doctor.getName())
            .surname(doctor.getSurname())
            .birthDate(doctor.getBirthDate())
            .sex(doctor.getSex())
            .address(doctor.getStudioAddress())
            .fiscalCode(doctor.getFiscalCode())
            .phoneNumber(doctor.getPhoneNumber())
            .municipality(doctor.getMunicipality().getMunicipality())
            .email(doctor.getEmail())
            .doctorId(doctor.getId())
            .build();
  }

}
