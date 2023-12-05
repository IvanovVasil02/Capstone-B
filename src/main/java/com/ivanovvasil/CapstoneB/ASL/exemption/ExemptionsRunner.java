package com.ivanovvasil.CapstoneB.ASL.exemption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ExemptionsRunner implements CommandLineRunner {
  @Autowired
  ExemptionsService es;

  @Override
  public void run(String... args) throws Exception {
    String ExemptionsListUrl = "src/main/java/com/ivanovvasil/CapstoneB/ASL/exemption/ExemptionsCodeList.csv";

    es.readExemptionFileCsv(ExemptionsListUrl);
  }
}
