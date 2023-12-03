//package com.ivanovvasil.CapstoneB.user;
//
//import com.ivanovvasil.CapstoneB.doctor.DoctorsService;
//import com.ivanovvasil.CapstoneB.patient.services.PatientsService;
//import com.ivanovvasil.CapstoneB.security.JWTTools;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class UserAuhtentication {
//  @Autowired
//  JWTTools jwtTools;
//
//  @Autowired
//  PatientsService ps;
//  @Autowired
//  DoctorsService ds;
//
//
//  public String authenticateUser(UserLoginDTO body) {
//
//    User patient = ps.getPatientByEmail(body.email());
//
//    if
//
//    if (bcrypt.matches(body.password(), user.getPassword())) {
//      return jwtTools.createToken(user);
//    } else {
//      throw new UnauthorizedException("Email or password invalid.");
//    }
//  }
//}
