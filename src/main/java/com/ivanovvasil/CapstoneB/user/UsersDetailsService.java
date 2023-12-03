package com.ivanovvasil.CapstoneB.user;

import com.ivanovvasil.CapstoneB.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersDetailsService implements UserDetailsService {
  @Autowired
  UserRepository ur;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = ur.findByEmail(email);
    if (user == null) {
      throw new NotFoundException(email);
    }
    return new CustomUserDetails(user);
  }
}
