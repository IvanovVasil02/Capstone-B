package com.ivanovvasil.CapstoneB.user;

import com.ivanovvasil.CapstoneB.exceptions.UnauthorizedException;
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
  PasswordEncoder bcrypt;

  public String authenticateUser(UserLoginDTO body) {

    User user = us.findByEmail(body.email());

    if (bcrypt.matches(body.password(), user.getPassword())) {
      return jwtTools.createToken(user);
    } else {
      throw new UnauthorizedException("Email or password invalid.");
    }
  }
}
