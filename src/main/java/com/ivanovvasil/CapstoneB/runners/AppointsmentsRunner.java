package com.ivanovvasil.CapstoneB.runners;

import com.ivanovvasil.CapstoneB.appointment.Appointment;
import com.ivanovvasil.CapstoneB.appointment.AppointmentStatus;
import com.ivanovvasil.CapstoneB.appointment.AppointmentsService;
import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.doctor.DoctorsService;
import com.ivanovvasil.CapstoneB.patient.Patient;
import com.ivanovvasil.CapstoneB.patient.PatientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

import static com.ivanovvasil.CapstoneB.tools.Tools.generateRandomLocalTime;
import static com.ivanovvasil.CapstoneB.tools.Tools.getRandomLocalCurentDate;

@Component
@Order(9)
public class AppointsmentsRunner implements ApplicationRunner {
  @Autowired
  DoctorsService ds;
  @Autowired
  PatientsService ps;
  @Autowired
  AppointmentsService as;

  private Boolean executed = true;

  @Override
  public void run(ApplicationArguments args) throws Exception {

    if (!executed) {
      List<Patient> patientList = ps.getAllPatients();
      List<Doctor> doctorList = ds.getAll();

      for (int i = 0; i < 200; i++) {
        Patient patient = patientList.get(new Random().nextInt(0, patientList.size() - 1));
        Doctor doctor = doctorList.get(new Random().nextInt(0, doctorList.size() - 1));

        Appointment appointment = Appointment.builder()
                .date(getRandomLocalCurentDate())
                .time(generateRandomLocalTime())
                .status(AppointmentStatus.ACCEPTED)
                .doctor(doctor)
                .patient(patient)
                .build();
        as.save(appointment);

        Appointment appointment1 = Appointment.builder()
                .date(getRandomLocalCurentDate())
                .time(generateRandomLocalTime())
                .status(AppointmentStatus.PENDING)
                .doctor(doctor)
                .patient(patient)
                .build();
        as.save(appointment1);
      }

      executed = true;
    }
  }
}
