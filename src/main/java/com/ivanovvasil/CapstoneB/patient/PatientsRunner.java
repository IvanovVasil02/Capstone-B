package com.ivanovvasil.CapstoneB.patient;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

@Component
public class PatientsRunner implements CommandLineRunner {


  @Override
  public void run(String... args) throws Exception {
    Faker faker = new Faker(Locale.ITALY);
    String surname = "Ivanov";
    String name = "Vasil Stanislavov".replaceAll(" ", "");
    StringBuilder fiscalCode = new StringBuilder();
    LocalDate birthDate = LocalDate.of(2002, 10, 28);

    int consonantCounter = 0;
    int consonantCounter2 = 0;

    for (int i = 0; i < surname.length() && consonantCounter < 3; i++) {
      char currentChar = surname.charAt(i);
      if (!isVocal(currentChar)) {
        fiscalCode.append(currentChar);
        consonantCounter++;
      }
    }

    if (consonantCounter < 3) {
      for (int i = 0; consonantCounter < 3; i++) {
        char currentChar = surname.charAt(i);
        if (isVocal(currentChar)) {
          fiscalCode.append(currentChar);
          consonantCounter++;
        }
      }
    }

    for (int i = 0; i < name.length() && consonantCounter2 < 4; i++) {
      char currentChar = name.charAt(i);
      if (!isVocal(currentChar)) {
        if (consonantCounter2 == 1) {
          consonantCounter2++;
          continue;
        }
        fiscalCode.append(currentChar);
        consonantCounter2++;
      }
    }

    fiscalCode.append(String.valueOf(birthDate.getYear()), 2, 4);
    fiscalCode.append(getMonth(birthDate.getMonthValue()));

    fiscalCode.append(getBirthDay(birthDate.getDayOfMonth(), "M"));
    fiscalCode.append(getBirthPlace("Bulgaria"));
    fiscalCode.append(getControllLetter());

    System.out.println(fiscalCode);
    System.out.println(consonantCounter);
    System.out.println(consonantCounter2);
    System.out.println(name);
  }

  public boolean isVocal(char c) {
    return "aeiouAEIOU".indexOf(c) != -1;
  }

  public String getMonth(int month) {

    Map<Integer, String> monthsLetter = new HashMap<>();
    monthsLetter.put(1, "A");
    monthsLetter.put(2, "B");
    monthsLetter.put(3, "C");
    monthsLetter.put(4, "D");
    monthsLetter.put(5, "E");
    monthsLetter.put(6, "H");
    monthsLetter.put(7, "L");
    monthsLetter.put(8, "M");
    monthsLetter.put(9, "P");
    monthsLetter.put(10, "R");
    monthsLetter.put(11, "S");
    monthsLetter.put(12, "T");
    return monthsLetter.get(month);
  }

  public String getBirthDay(int birthDay, String sex) {

    if (sex.equals("F")) {
      return String.valueOf(40 + birthDay);
    }

    if (String.valueOf(birthDay).length() < 2) {
      return "0" + birthDay;
    }
    return String.valueOf(birthDay);
  }

  public String getBirthPlace(String municipality) {
    return municipality.charAt(0) + String.valueOf(new Random().nextInt(100, 999));
  }

  public char getControllLetter() {
    return "ABCDEFGHILMNOPQRSTUVWXYZ".charAt(new Random().nextInt(0, 25));
  }
}
