package com.ivanovvasil.CapstoneB.Medicine;

import com.ivanovvasil.CapstoneB.Medicine.services.MedicinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MedicineRunner implements CommandLineRunner {

  @Autowired
  MedicinesService ms;
  @Override
  public void run(String... args) throws Exception {
    String meidicineListUrl = "src/main/java/com/ivanovvasil/CapstoneB/Medicine/medicineList.csv";

    ms.readMedicineFileCsv(meidicineListUrl);
  }
}
