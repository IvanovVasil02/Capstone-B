package com.ivanovvasil.CapstoneB.prescription;

import com.ivanovvasil.CapstoneB.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PrescriptionsService {
  @Autowired
  PrescriptionRepo pr;

  public Prescription save(Prescription prescription) {
    return pr.save(prescription);
  }

  public Prescription finddById(UUID id) {
    return pr.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public List<Prescription> finddAllPrescriptionById(UUID id) {
    return pr.findAllByPatientId(id);
  }

  public void delete(UUID id) {
    pr.deleteById(id);
  }


}
