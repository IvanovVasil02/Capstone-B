package com.ivanovvasil.CapstoneB.ASL.exemption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class ExemptionsRunner implements ApplicationRunner {
  @Autowired
  ExemptionsService es;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    String exemptionsListUrl = "src/main/java/com/ivanovvasil/CapstoneB/ASL/exemption/ExemptionsCodeList.csv";
    es.readExemptionFileCsv(exemptionsListUrl);
  }
}
