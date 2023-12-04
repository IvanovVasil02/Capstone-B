package com.ivanovvasil.CapstoneB.user;

import com.ivanovvasil.CapstoneB.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersService {
  @Autowired
  UserRepository ur;

  public User findById(UUID id) {
    return ur.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public User findByEmail(String email) {
    return ur.findByEmail(email);
  }
}
