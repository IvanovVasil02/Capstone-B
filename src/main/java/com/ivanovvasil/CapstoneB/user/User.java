package com.ivanovvasil.CapstoneB.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String name;
  private String surname;
  private LocalDate birthDate;
  private String sex;
  private String fiscalCode;
  private String address;
  private String email;
  private String password;
  private String phoneNumber;
  private String province;
  private String region;
}
