package com.ivanovvasil.CapstoneB.user;

import com.ivanovvasil.CapstoneB.patient.payloads.PatientDTO;
import com.ivanovvasil.CapstoneB.user.payloads.Token;
import com.ivanovvasil.CapstoneB.user.payloads.UserLoginDTO;
import com.ivanovvasil.CapstoneB.user.services.UserAuhtenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
  @ResponseStatus(HttpStatus.CREATED)
  public void login(@RequestBody PatientDTO body) throws Exception {
    uas.registerPatient(body);
  }
}
