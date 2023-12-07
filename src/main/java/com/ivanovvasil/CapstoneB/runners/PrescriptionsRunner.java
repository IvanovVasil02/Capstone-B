package com.ivanovvasil.CapstoneB.runners;

import com.ivanovvasil.CapstoneB.Medicine.Medicine;
import com.ivanovvasil.CapstoneB.Medicine.services.MedicinesService;
import com.ivanovvasil.CapstoneB.patient.Patient;
import com.ivanovvasil.CapstoneB.patient.services.PatientsService;
import com.ivanovvasil.CapstoneB.prescription.Prescription;
import com.ivanovvasil.CapstoneB.prescription.PrescriptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Order(7)
public class PrescriptionsRunner implements ApplicationRunner {
  @Autowired
  MedicinesService ms;
  @Autowired
  PatientsService ps;
  @Autowired
  PrescriptionsService prs;
  private Boolean executed = true;

  @Override
  public void run(ApplicationArguments args) throws Exception {

    if (!executed) {
      List<Patient> patientList = ps.getAllPatients();
      List<Medicine> medicineList = ms.getAllMedicines();


      for (int i = 0; i < 50; i++) {
        Patient currentPatient = patientList.get(new Random().nextInt(0, patientList.size()));
        List<Medicine> randomPrescription = new ArrayList<>();
        for (int j = 0; j < new Random().nextInt(0, 3); j++) {
          randomPrescription.add(medicineList.get(new Random().nextInt(0, medicineList.size())));
        }
        Prescription prescription = Prescription.builder()
                .patient(currentPatient)
                .doctor(currentPatient.getDoctor())
                .prescription(randomPrescription)
                .packagesNumber(new Random().nextInt(1, 6))
                .region(currentPatient.getDoctor().getRegion())
                .localHealthCode(currentPatient.getHealthCompanyCode())
                .build();
        prs.save(prescription);
      }
      executed = true;
    }
  }
}
