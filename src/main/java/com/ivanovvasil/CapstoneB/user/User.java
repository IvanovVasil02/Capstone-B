package com.ivanovvasil.CapstoneB.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.ivanovvasil.CapstoneB.tools.Tools.calculateFiscalCode;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties({"createdAt", "password", "authorities", "bills", "enabled", "credentialsNonExpired", "accountNonExpired", "accountNonLocked"})
public class User implements UserDetails {
  protected String name;
  protected String surname;
  protected LocalDate birthDate;
  protected String sex;
  protected String fiscalCode;
  protected String address;
  protected String email;
  protected String password;
  protected String phoneNumber;
  protected String municipality;
  protected String region;
  @Enumerated(EnumType.STRING)
  protected UserRole role;
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;


  public User(String name, String surname, LocalDate birthDate, String sex, String address, String email, String password, String phoneNumber, String municipality, String region, UserRole role) {
    this.name = name;
    this.surname = surname;
    this.birthDate = birthDate;
    this.sex = sex;
    this.fiscalCode = calculateFiscalCode(surname, name, sex, birthDate);
    this.address = address;
    this.email = email;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.municipality = municipality;
    this.region = region;
    this.role = role;
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(this.role.name()));
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
