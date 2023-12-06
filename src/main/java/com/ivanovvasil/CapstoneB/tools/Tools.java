package com.ivanovvasil.CapstoneB.tools;

import java.time.LocalDate;
import java.util.Random;


public class Tools {


  public static LocalDate getRandomLocalDate() {
    LocalDate startDate = LocalDate.of(1980, 1, 1);
    LocalDate endDate = LocalDate.now();

    long giorniInizio = startDate.toEpochDay();
    long giorniFine = endDate.toEpochDay();

    Random random = new Random();
    long giorniCasuali = giorniInizio + random.nextLong(giorniFine - giorniInizio);

    return LocalDate.ofEpochDay(giorniCasuali);
  }

  public static String calculateFiscalCode(String surname, String name, String sex, LocalDate birthDate) {
    FiscalCodeService fs = new FiscalCodeService();
    StringBuilder fiscalCode = new StringBuilder();
    fiscalCode.append(fs.getConsonantFromString(surname, 3));
    fiscalCode.append(fs.getConsonantFromString(name, 4));
    fiscalCode.append(String.valueOf(birthDate.getYear()), 2, 4);
    fiscalCode.append(fs.getMonth(birthDate.getMonthValue()));
    fiscalCode.append(fs.getBirthDay(birthDate.getDayOfMonth(), "M"));
    fiscalCode.append(fs.getControllLetter());
    return String.valueOf(fiscalCode).toUpperCase();
  }
}
