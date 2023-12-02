package com.ivanovvasil.CapstoneB.ASL.ASLCodes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class Runner implements CommandLineRunner {
  @Autowired
  ASLService as;

  @Override
  public void run(String... args) throws Exception {
    String ASLListUrl = "src/main/java/com/ivanovvasil/CapstoneB/ASL/ASLCodes/ASLRegoinCodes.csv";

    as.readASLFileCsv(ASLListUrl);
  }
}
