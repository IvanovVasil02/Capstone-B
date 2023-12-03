package com.ivanovvasil.CapstoneB.user;

import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.patient.Patient;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomUserDetails implements UserDetails {
  private String email;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;

  public CustomUserDetails(User user) {
    this.email = user.getEmail();
    this.password = user.getPassword();
    this.authorities = Collections.singleton(new SimpleGrantedAuthority(getRole(user)));
  }

  private String getRole(User user) {
    if (user instanceof Doctor) {
      return "DOCTOR";
    } else if (user instanceof Patient) {
      return "PATIENT";
    } else {
      throw new IllegalArgumentException("User type not supported");
    }
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }


  @Override
  public String getUsername() {
    return this.email;
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
