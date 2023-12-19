package com.ivanovvasil.CapstoneB.user;

import com.ivanovvasil.CapstoneB.patient.Patient;
import com.ivanovvasil.CapstoneB.patient.payloads.PatientDTO;
import com.ivanovvasil.CapstoneB.user.payloads.Token;
import com.ivanovvasil.CapstoneB.user.payloads.UserLoginDTO;
import com.ivanovvasil.CapstoneB.user.services.UserAuhtenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class UsersController {
  @Autowired
  UserAuhtenticationService uas;

  @PostMapping("/login")
  public Token login(@RequestBody UserLoginDTO body) throws Exception {
    return new Token(uas.authenticateUser(body));
  }

  @PostMapping("/register")
  public Patient login(@RequestBody PatientDTO body) throws Exception {
    return uas.registerPatient(body);
  }
}
