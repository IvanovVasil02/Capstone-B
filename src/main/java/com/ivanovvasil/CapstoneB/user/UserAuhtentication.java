package com.ivanovvasil.CapstoneB.user;

import com.ivanovvasil.CapstoneB.ASL.ASLCodes.ASL;
import com.ivanovvasil.CapstoneB.ASL.ASLCodes.ASLService;
import com.ivanovvasil.CapstoneB.exceptions.UnauthorizedException;
import com.ivanovvasil.CapstoneB.patient.Patient;
import com.ivanovvasil.CapstoneB.patient.payloads.PatientDTO;
import com.ivanovvasil.CapstoneB.patient.services.PatientsService;
import com.ivanovvasil.CapstoneB.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuhtentication {
  @Autowired
  JWTTools jwtTools;
  @Autowired
  UsersService us;
  @Autowired
  PatientsService ps;
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

  public void registerPatient(PatientDTO body) {
    us.findByEmail(body.email());
    ASL asl = asls.getAslByMunicipality(body.municipality());
    String userHealthCompanyCode = asl.getCompanyCode();
    String userRegion = asl.getRegionDenomination();
    Patient patient = new Patient(body.name(), body.surname(), body.birthDate(),
            body.sex(), body.fiscalCode(), body.address(), body.email(),
            body.password(), body.doctor().phoneNumber, body.municipality(),
            userRegion, UserRole.PATIENT, body.doctor(), userHealthCompanyCode);
    ps.save(patient);
  }

}
