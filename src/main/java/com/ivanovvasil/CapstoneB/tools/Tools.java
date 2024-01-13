package com.ivanovvasil.CapstoneB.tools;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Tools {


  public static LocalDate getRandomLocalDate(int startYear) {
    LocalDate startDate = LocalDate.of(startYear, 1, 1);
    LocalDate endDate = LocalDate.now();

    long startEpochDay = startDate.toEpochDay();
    long endEpochDay = endDate.toEpochDay();

    Random random = new Random();
    long randomEpochDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);

    return LocalDate.ofEpochDay(randomEpochDay);

  }

  public static LocalTime generateRandomLocalTime() {
    Random random = new Random();

    int hour = random.nextInt(24);       // da 0 a 23
    int minute = random.nextInt(60);     // da 0 a 59
    int second = random.nextInt(60);     // da 0 a 59
    int nano = random.nextInt(999_999_999 + 1); // da 0 a 999_999_999

    return LocalTime.of(hour, minute, second, nano);
  }

  public static LocalDate getRandomLocalCurentDate() {
    LocalDate startDate = LocalDate.of(2023, 1, 1);
    LocalDate endDate = LocalDate.now();

    long epochStartDate = startDate.toEpochDay();
    long epochEndDate = endDate.toEpochDay();

    Random random = new Random();
    long giorniCasuali = epochStartDate + random.nextLong(epochEndDate - epochStartDate);

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
