package com.ivanovvasil.CapstoneB.runners;

import com.ivanovvasil.CapstoneB.Medicine.Medicine;
import com.ivanovvasil.CapstoneB.Medicine.MedicinesService;
import com.ivanovvasil.CapstoneB.patient.Patient;
import com.ivanovvasil.CapstoneB.patient.PatientsService;
import com.ivanovvasil.CapstoneB.prescription.Prescription;
import com.ivanovvasil.CapstoneB.prescription.PrescriptionDetails;
import com.ivanovvasil.CapstoneB.prescription.PrescriptionDetailsRepo;
import com.ivanovvasil.CapstoneB.prescription.PrescriptionsService;
import com.ivanovvasil.CapstoneB.prescription.enums.PrescriptionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Order(8)
public class PrescriptionsRunner implements ApplicationRunner {
  @Autowired
  MedicinesService ms;
  @Autowired
  PatientsService ps;
  @Autowired
  PrescriptionsService prs;
  @Autowired
  PrescriptionDetailsRepo prsd;
  private Boolean executed = true;

  @Override
  public void run(ApplicationArguments args) throws Exception {

    if (!executed) {
      List<Patient> patientList = ps.getAllPatients();
      List<Medicine> medicineList = ms.getAllMedicines();

      for (int i = 0; i < 200; i++) {
        Patient currentPatient = patientList.get(new Random().nextInt(0, patientList.size()));
        List<PrescriptionDetails> prescriptionDetailsList = new ArrayList<>();

        Prescription prescription = Prescription.builder()
                .patient(currentPatient)
                .doctor(currentPatient.getDoctor())
                .packagesNumber(new Random().nextInt(1, 6))
                .region(currentPatient.getDoctor().getMunicipality().getRegion())
                .provinceAbbr(currentPatient.getMunicipality().getProvinceAbbr())
                .localHealthCode(currentPatient.getHealthCompanyCode())
                .status(PrescriptionStatus.APPROVED)
//                .issuingDate(getRandomLocalDate(2023))
                .build();

        for (int j = 0; j < new Random().nextInt(1, 3); j++) {
          Medicine medicine = medicineList.get(new Random().nextInt(medicineList.size()));

          PrescriptionDetails prescriptionDetails = PrescriptionDetails.builder()
                  .medicine(medicine)
                  .quantity(new Random().nextInt(1, 2))
                  .prescription(prescription)
                  .build();
          prescriptionDetailsList.add(prescriptionDetails);
        }

        prescription.setPrescription(prescriptionDetailsList);
        prs.save(prescription);

        for (PrescriptionDetails prescriptionDetails : prescriptionDetailsList) {
          prescriptionDetails.setPrescription(prescription);
          prsd.save(prescriptionDetails);
        }
        prs.verifyPrsecription(currentPatient.getPrescriptions(), prescription);
      }
      for (int i = 0; i < 200; i++) {
        Patient currentPatient = patientList.get(new Random().nextInt(0, patientList.size()));
        List<PrescriptionDetails> prescriptionDetailsList = new ArrayList<>();

        Prescription prescription = Prescription.builder()
                .patient(currentPatient)
                .doctor(currentPatient.getDoctor())
                .packagesNumber(new Random().nextInt(1, 6))
                .region(currentPatient.getDoctor().getMunicipality().getRegion())
                .localHealthCode(currentPatient.getHealthCompanyCode())
                .provinceAbbr(currentPatient.getMunicipality().getProvinceAbbr())
                .status(PrescriptionStatus.PENDING)
//                .issuingDate(getRandomLocalDate(2023))
                .build();

        for (int j = 0; j < new Random().nextInt(1, 3); j++) {
          Medicine medicine = medicineList.get(new Random().nextInt(medicineList.size()));

          PrescriptionDetails prescriptionDetails = PrescriptionDetails.builder()
                  .medicine(medicine)
                  .quantity(new Random().nextInt(1, 2))
                  .prescription(prescription)
                  .build();
          prescriptionDetailsList.add(prescriptionDetails);
        }

        prescription.setPrescription(prescriptionDetailsList);
        prs.save(prescription);

        for (PrescriptionDetails prescriptionDetails : prescriptionDetailsList) {
          prescriptionDetails.setPrescription(prescription);
          prsd.save(prescriptionDetails);
        }
        prs.verifyPrsecription(currentPatient.getPrescriptions(), prescription);
      }

      executed = true;
    }
  }
}
