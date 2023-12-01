package com.ivanovvasil.CapstoneB.patient.services;

import com.ivanovvasil.CapstoneB.exceptions.NotFoundException;
import com.ivanovvasil.CapstoneB.patient.Patient;
import com.ivanovvasil.CapstoneB.patient.PatientsRepo;
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

  public void save(Patient patient) {
    pr.save(patient);
  }

  public Page<Patient> getAll(int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    return pr.findAll(pageable);
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
}
