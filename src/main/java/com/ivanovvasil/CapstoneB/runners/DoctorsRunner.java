package com.ivanovvasil.CapstoneB.runners;

import com.github.javafaker.Faker;
import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.doctor.DoctorsService;
import com.ivanovvasil.CapstoneB.municipality.Municipality;
import com.ivanovvasil.CapstoneB.municipality.MunicipalityService;
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
@Order(5)
public class DoctorsRunner implements ApplicationRunner {
  @Autowired
  DoctorsService ds;
  @Autowired
  MunicipalityService ms;
  @Autowired
  PasswordEncoder bcrypt;
  private boolean executed = true;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    if (!executed) {
      Faker faker = new Faker(Locale.ITALY);
      List<Municipality> municipalities = ms.getAll();
      String[] sex = {"M", "F"};

      for (int i = 0; i < 10; i++) {
        Municipality municipality = municipalities.get(new Random().nextInt(0, municipalities.size() - 1));
        String name = faker.name().firstName();
        String surname = faker.name().lastName();
        Doctor doctor = new Doctor(name,
                surname,
                getRandomLocalDate(1985),
                sex[new Random().nextInt(0, 1)],
                faker.address().streetAddress(),
                faker.address().streetAddress(),
                municipality,
                (name + "." + surname + "@gmail.com").toLowerCase(),
                bcrypt.encode("12345"),
                faker.phoneNumber().phoneNumber(),
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
