package com.ivanovvasil.CapstoneB.user;

import com.ivanovvasil.CapstoneB.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class UsersService {
  @Autowired
  UserRepository ur;
  @Autowired
  FiscalCodeService fs;

  public User findById(UUID id) {
    return ur.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public User findByEmail(String email) {
    return ur.findByEmail(email);
  }

  public String calculateFiscalCode(String surname, String name, String sex, String birthPlace, String provinceAbbreviation, LocalDate birthDate) {
    StringBuilder fiscalCode = new StringBuilder();
    fiscalCode.append(fs.getConsonatFromString(surname, 3));
    fiscalCode.append(fs.getConsonatFromString(name, 4));
    fiscalCode.append(String.valueOf(birthDate.getYear()), 2, 4);
    fiscalCode.append(fs.getMonth(birthDate.getMonthValue()));
    fiscalCode.append(fs.getBirthDay(birthDate.getDayOfMonth(), "M"));
    fiscalCode.append(fs.getControllLetter());
    return String.valueOf(fiscalCode);
  }

}
