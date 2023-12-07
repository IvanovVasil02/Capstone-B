package com.ivanovvasil.CapstoneB.runners;

import com.ivanovvasil.CapstoneB.ASL.exemption.Exemption;
import com.ivanovvasil.CapstoneB.ASL.exemption.ExemptionsService;
import com.ivanovvasil.CapstoneB.patient.Patient;
import com.ivanovvasil.CapstoneB.patient.PatientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@Order(6)
public class ExemptionsPatientsRunner implements ApplicationRunner {
  @Autowired
  PatientsService ps;
  @Autowired
  ExemptionsService es;
  private boolean executed = true;


  @Override
  public void run(ApplicationArguments args) throws Exception {
    if (!executed) {
      List<Patient> patientList = ps.getAllPatients();
      List<Exemption> exemptions = es.getAll();

      for (int i = 0; i < 50; i++) {
        Patient patient = patientList.get(new Random().nextInt(0, patientList.size()));
        Exemption ex = exemptions.get(new Random().nextInt(0, exemptions.size()));
        Exemption ex1 = exemptions.get(new Random().nextInt(0, exemptions.size()));

        patient.setExemptions(Arrays.asList(ex, ex1));
        ps.save(patient);
      }
      executed = false;
    }
  }
}
