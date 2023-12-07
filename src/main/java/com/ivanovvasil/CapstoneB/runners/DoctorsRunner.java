package com.ivanovvasil.CapstoneB.runners;

import com.github.javafaker.Faker;
import com.ivanovvasil.CapstoneB.ASL.ASLCodes.ASL;
import com.ivanovvasil.CapstoneB.ASL.ASLCodes.ASLService;
import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.doctor.DoctorsService;
import com.ivanovvasil.CapstoneB.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static com.ivanovvasil.CapstoneB.tools.Tools.getRandomLocalDate;

@Component
@Order(4)
public class DoctorsRunner implements ApplicationRunner {
  @Autowired
  DoctorsService ds;
  @Autowired
  ASLService as;
  @Autowired
  PasswordEncoder bcrypt;
  private boolean executed = true;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    if (!executed) {
      Faker faker = new Faker(Locale.ITALY);
      List<ASL> aslList = as.getAll();
      String[] sex = {"M", "F"};

      for (int i = 0; i < 10; i++) {
        ASL asl = aslList.get(new Random().nextInt(0, aslList.size() - 1));
        Doctor doctor = new Doctor(faker.name().firstName(),
                faker.name().lastName(),
                getRandomLocalDate(),
                sex[new Random().nextInt(0, 1)],
                faker.address().streetAddress(),
                faker.internet().emailAddress(),
                bcrypt.encode("12345"),
                faker.phoneNumber().phoneNumber(),
                asl.getMunicipalityDenomination(),
                asl.getRegionDenomination(),
                faker.code().ean8(),
                faker.code().ean8(),
                LocalDate.now().plusYears(5),
                faker.phoneNumber().phoneNumber(),
                UserRole.DOCTOR
        );
        ds.save(doctor);
      }
      executed = true;
    }
  }
}
