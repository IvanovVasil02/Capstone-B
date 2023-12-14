package com.ivanovvasil.CapstoneB.runners;

import com.ivanovvasil.CapstoneB.municipality.MunicipalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class MunicipalityRunner implements CommandLineRunner {
  @Autowired
  MunicipalityService ms;

  @Override
  public void run(String... args) throws Exception {
    String ASLListUrl = "src/main/java/com/ivanovvasil/CapstoneB/municipality/municipalityList.csv";

    ms.readMunicipalityFileCsv(ASLListUrl);
  }
}
