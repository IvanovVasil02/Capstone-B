package com.ivanovvasil.CapstoneB.user.services;

import com.ivanovvasil.CapstoneB.ASL.ASLCodes.ASL;
import com.ivanovvasil.CapstoneB.ASL.ASLCodes.ASLService;
import com.ivanovvasil.CapstoneB.doctor.Doctor;
import com.ivanovvasil.CapstoneB.doctor.DoctorsService;
import com.ivanovvasil.CapstoneB.exceptions.BadRequestException;
import com.ivanovvasil.CapstoneB.exceptions.UnauthorizedException;
import com.ivanovvasil.CapstoneB.patient.Patient;
import com.ivanovvasil.CapstoneB.patient.PatientsService;
import com.ivanovvasil.CapstoneB.patient.payloads.PatientDTO;
import com.ivanovvasil.CapstoneB.security.JWTTools;
import com.ivanovvasil.CapstoneB.user.User;
import com.ivanovvasil.CapstoneB.user.UserRepository;
import com.ivanovvasil.CapstoneB.user.UserRole;
import com.ivanovvasil.CapstoneB.user.payloads.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserAuhtenticationService {
  @Autowired
  JWTTools jwtTools;
  @Autowired
  UsersService us;
  @Autowired
  PatientsService ps;
  @Autowired
  DoctorsService ds;
  @Autowired
  UserRepository ur;
  @Autowired
  PasswordEncoder bcrypt;
  @Autowired
  ASLService asls;

  public String authenticateUser(UserLoginDTO body) {
    User user = us.findByEmail(body.email());
    if (bcrypt.matches(body.password(), user.getPassword())) {
      return jwtTools.createToken(user);
    } else {
      throw new UnauthorizedException("Email or password invalid.");
    }
  }

  public Patient registerPatient(PatientDTO body) {
    ur.findByEmail(body.email()).ifPresent(patient -> {
      throw new BadRequestException("The email " + patient.getEmail() + " is alredy used.");
    });
    Doctor doctor = ds.findById(UUID.fromString(body.doctorId()));
    ASL asl = asls.getAslByMunicipality(body.municipality());
    String userHealthCompanyCode = asl.getCompanyCode();
    String userRegion = asl.getRegionDenomination();
    Patient patient = new Patient(body.name(), body.surname(), body.birthDate(),
            body.sex(), body.address(), body.email(),
            bcrypt.encode(body.password()), body.phoneNumber(), body.municipality(),
            userRegion, UserRole.PATIENT, doctor);
    return ps.save(patient);
  }

}
