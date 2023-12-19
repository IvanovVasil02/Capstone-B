package com.ivanovvasil.CapstoneB.runners;

import com.github.javafaker.Faker;
import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.doctor.DoctorsService;
import com.ivanovvasil.CapstoneB.municipality.Municipality;
import com.ivanovvasil.CapstoneB.municipality.MunicipalityService;
import com.ivanovvasil.CapstoneB.patient.Patient;
import com.ivanovvasil.CapstoneB.patient.PatientsService;
import com.ivanovvasil.CapstoneB.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import static com.ivanovvasil.CapstoneB.tools.Tools.getRandomLocalDate;

@Component
@Order(6)
public class PatientsRunner implements ApplicationRunner {
  @Autowired
  PatientsService ps;
  @Autowired
  DoctorsService ds;
  @Autowired
  MunicipalityService ms;
  @Autowired
  PasswordEncoder bcrypt;
  private boolean executed = false;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    if (!executed) {
      Faker faker = new Faker(Locale.ITALY);
      List<Municipality> municipalities = ms.getAll();
      List<Doctor> doctorList = ds.getAll();
      String[] sex = {"M", "F"};

      for (int i = 0; i < 50; i++) {
        Municipality municipality = municipalities.get(new Random().nextInt(0, municipalities.size() - 1));
        Patient patient = new Patient(faker.name().firstName(),
                faker.name().lastName(),
                getRandomLocalDate(),
                sex[new Random().nextInt(0, 1)],
                faker.address().streetAddress(),
                municipality,
                faker.internet().emailAddress(),
                bcrypt.encode("12345"),
                faker.phoneNumber().phoneNumber(),
                UserRole.PATIENT,
                doctorList.get(new Random().nextInt(0, doctorList.size() - 1))
        );
        ps.save(patient);

      }
      executed = true;
    }
  }
}
