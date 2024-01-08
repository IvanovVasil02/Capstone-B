package com.ivanovvasil.CapstoneB.user;

import com.ivanovvasil.CapstoneB.exceptions.BadRequestException;
import com.ivanovvasil.CapstoneB.patient.payloads.PatientDTO;
import com.ivanovvasil.CapstoneB.user.payloads.Token;
import com.ivanovvasil.CapstoneB.user.payloads.UserLoginDTO;
import com.ivanovvasil.CapstoneB.user.services.UserAuhtenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
public class UsersController {
  @Autowired
  UserAuhtenticationService uas;

  @PostMapping("/login")
  public Token login(@Validated @RequestBody UserLoginDTO body, BindingResult validation) throws Exception {
    if (validation.hasErrors()) {
      throw new BadRequestException("Empty or not respected fields", validation.getAllErrors());
    } else {
      return new Token(uas.authenticateUser(body));
    }
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public void login(@Validated @RequestBody PatientDTO body, BindingResult validaiton) throws Exception {
    if (validaiton.hasErrors()) {
      throw new BadRequestException("Empty or not respected fields", validaiton.getAllErrors());
    } else {
      uas.registerPatient(body);
    }
  }
}
