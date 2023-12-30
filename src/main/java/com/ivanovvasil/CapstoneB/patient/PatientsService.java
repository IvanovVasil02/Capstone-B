package com.ivanovvasil.CapstoneB.patient;

import com.ivanovvasil.CapstoneB.ASL.ASLCodes.ASLService;
import com.ivanovvasil.CapstoneB.ASL.exemption.Exemption;
import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.doctor.DoctorsService;
import com.ivanovvasil.CapstoneB.exceptions.BadRequestException;
import com.ivanovvasil.CapstoneB.exceptions.NotFoundException;
import com.ivanovvasil.CapstoneB.patient.payloads.PatientResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientsService {

  @Autowired
  PatientsRepo pr;
  @Autowired
  ASLService aslService;
  @Autowired
  DoctorsService ds;

  public Patient save(Patient patient) {
    String aslCode = aslService.getAslByMunicipalityIstat(patient.getMunicipality().getIstat()).getCompanyCode();
    patient.setHealthCompanyCode(aslCode);
    return pr.save(patient);
  }

  public Page<Patient> getAll(int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    return pr.findAll(pageable);
  }

  public Page<PatientResponseDTO> getPatientsList(Doctor doctor, int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    Page<Patient> patients = pr.findByDoctorId(doctor.getId(), pageable);
    return patients.map(this::convertPatientResponse);
  }

  public List<Patient> getAllPatients() {
    return pr.findAll();
  }

  public Patient getPatientById(UUID id) {
    return pr.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public Patient getPatientByEmail(String email) {
    return pr.findByEmailIgnoreCase(email).orElseThrow(() -> new NotFoundException(email));
  }

  public Page<PatientResponseDTO> searchPatients(String q, Doctor doctor, String by, int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    if (by.equals("fiscalCode")) {
      Page<Patient> patients = pr.findByFiscalCodeStartingWithIgnoreCaseAndDoctorId(q, doctor.getId(), pageable);
      return patients.map(this::convertPatientResponse);
    } else if (by.equals("name")) {
      Page<Patient> patients = pr.findByNameStartingWithIgnoreCaseAndDoctorId(q, doctor.getId(), pageable);
      return patients.map(this::convertPatientResponse);
    }
    throw new BadRequestException("There is a problem whit the query!");
  }

  public void delete(UUID id) {
    Patient toRemove = this.getPatientById(id);
    pr.delete(toRemove);
  }

  public PatientResponseDTO convertPatientResponse(Patient patient) {
    return PatientResponseDTO.builder()
            .name(patient.getName())
            .surname(patient.getSurname())
            .birthDate(patient.getBirthDate())
            .sex(patient.getSex())
            .address(patient.getAddress())
            .fiscalCode(patient.getFiscalCode())
            .phoneNumber(patient.getPhoneNumber())
            .municipalityDenomination(patient.getMunicipality().getMunicipality())
            .municipality(patient.getMunicipality().getPostalCode())
            .email(patient.getEmail())
            .doctor(ds.convertToDoctorProfileDTO(patient.getDoctor()))
            .exemptions(patient.getExemptions().stream().map(Exemption::getExemptionCode).toList())
            .role(patient.getRole())
            .build();
  }
}
