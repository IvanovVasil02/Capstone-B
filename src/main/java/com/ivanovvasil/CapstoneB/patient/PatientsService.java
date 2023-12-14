package com.ivanovvasil.CapstoneB.patient;

import com.ivanovvasil.CapstoneB.ASL.ASLCodes.ASLService;
import com.ivanovvasil.CapstoneB.ASL.exemption.Exemption;
import com.ivanovvasil.CapstoneB.doctor.Doctor;
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

  public Patient save(Patient patient) {
    String aslCode = aslService.getAslByMunicipalityIstat(patient.getMunicipality().getIstat()).getCompanyCode();
    patient.setHealthCompanyCode(aslCode);
    return pr.save(patient);
  }

  public Page<Patient> getAll(int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    return pr.findAll(pageable);
  }

  public List<PatientResponseDTO> getPatientsList(Doctor doctor) {
    List<Patient> patientList = pr.findByDoctor(doctor);
    return patientList.stream().map(this::convertPatientResponse).toList();
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
            .municipality(patient.getMunicipality().getMunicipality())
            .email(patient.getEmail())
            .doctor(patient.getDoctor().getName() + " " + patient.getDoctor().getSurname())
            .exemptions(patient.getExemptions().stream().map(Exemption::getExemptionCode).toList())
            .build();
  }


}
