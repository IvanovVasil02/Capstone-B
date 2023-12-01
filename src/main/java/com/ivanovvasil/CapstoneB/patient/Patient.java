package com.ivanovvasil.CapstoneB.patient;

import com.ivanovvasil.CapstoneB.doctor.Doctor;

import java.util.UUID;

public class Patient {
  private UUID id;
  private String name;
  private String surname;
  private String birthDate;
  private String sex;
  private String fiscalCode;
  private String address;
  private String email;
  private String maritalStatus;
  private Doctor doctor;
}
