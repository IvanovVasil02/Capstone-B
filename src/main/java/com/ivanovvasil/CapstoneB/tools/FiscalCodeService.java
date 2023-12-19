package com.ivanovvasil.CapstoneB.tools;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class FiscalCodeService {
  public StringBuilder getConsonantFromString(String surname, int index) {
    int consonantCounter = 0;
    surname = surname.replaceAll(" ", "");
    StringBuilder fiscalCode = new StringBuilder();
    for (int i = 0; i < surname.length() && consonantCounter < index; i++) {
      char currentChar = surname.charAt(i);
      if (!isVocal(currentChar)) {
        if (index == 4 && consonantCounter == 1) {
          consonantCounter++;
          continue;
        }
        fiscalCode.append(currentChar);
        consonantCounter++;
      }
    }

    if (consonantCounter < index) {
      for (int i = 0; i < surname.length() && consonantCounter < index; i++) {
        char currentChar = surname.charAt(i);
        if (isVocal(currentChar)) {
          fiscalCode.append(currentChar);
          consonantCounter++;
        }
      }
    }
    return fiscalCode;
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

  public char getControllLetter() {
    return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(new Random().nextInt(0, 25));
  }
}
