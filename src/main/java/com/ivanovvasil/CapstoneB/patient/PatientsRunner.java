package com.ivanovvasil.CapstoneB.patient;

import com.github.javafaker.Faker;
import com.ivanovvasil.CapstoneB.ASL.ASLCodes.ASL;
import com.ivanovvasil.CapstoneB.ASL.ASLCodes.ASLService;
import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.doctor.DoctorsService;
import com.ivanovvasil.CapstoneB.patient.services.PatientsService;
import com.ivanovvasil.CapstoneB.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import static com.ivanovvasil.CapstoneB.tools.Tools.getRandomLocalDate;

@Component
@Order(5)
public class PatientsRunner implements ApplicationRunner {
  @Autowired
  PatientsService ps;
  @Autowired
  DoctorsService ds;
  @Autowired
  ASLService as;
  private boolean executed = true;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    if (!executed) {
      Faker faker = new Faker(Locale.ITALY);
      List<ASL> aslList = as.getAll();
      List<Doctor> doctorList = ds.getAll();
      String[] sex = {"M", "F"};

      for (int i = 0; i < 50; i++) {
        ASL asl = aslList.get(new Random().nextInt(0, aslList.size() - 1));
        Patient patient = new Patient(faker.name().firstName(),
                faker.name().lastName(),
                getRandomLocalDate(),
                sex[new Random().nextInt(0, 1)],
                faker.address().streetAddress(),
                faker.internet().emailAddress(),
                "12345",
                faker.phoneNumber().phoneNumber(),
                asl.getMunicipalityDenomination(),
                asl.getRegionDenomination(),
                UserRole.PATIENT,
                doctorList.get(new Random().nextInt(0, doctorList.size() - 1))
        );
        ps.save(patient);

      }
      executed = true;
    }
  }
}
