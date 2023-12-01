package com.ivanovvasil.CapstoneB.patient;

import com.ivanovvasil.CapstoneB.doctor.Doctor;

import java.time.LocalDate;
import java.util.UUID;

public class Patient {
  private UUID id;
  private String name;
  private String surname;
  private LocalDate birthDate;
  private String sex;
  private String fiscalCode;
  private String address;
  private String email;
  private String password;
  private Doctor doctor;
  
}
