package com.ivanovvasil.CapstoneB.Medicine.services;

import com.ivanovvasil.CapstoneB.Medicine.Medicine;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MedicinesService {

@Autowired
  private MedicineRepo mr;

  public void save(Medicine province) {
    mr.save(province);
  }

  public List<Medicine> getAllMedicines() {
    return mr.findAll();
  }

  public void readProvinceFileCsv(String path) throws IOException {
    if (mr.findAll().isEmpty()) {
      try {
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        CSVReader reader = new CSVReaderBuilder(new FileReader(path)).withCSVParser(parser).withSkipLines(1).build();
        List<String[]> medicineRows = reader.readAll();
        for (String[] medicineRow : medicineRows) {
          Medicine medicine = this.createMedicine(medicineRow);
          this.save(medicine);
        }
      } catch (CsvException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private Medicine createMedicine(String[] line) {
    try {
      Medicine medicine = new Medicine();
     medicine.setActiveIngredient(line[0]);
     medicine.setGroupDescription(line[1]);
     medicine.setNameAndPackaginf(line[2]);
     medicine.setPublicPrice(line[3]);
     medicine.setHolderOfMarketingAuthorization(line[4]);
     medicine.setMarketingAuthorization(line[5]);
     medicine.setXInAifaTransparencyList(line[6]);
     medicine.setXInRegionList(line[7]);
     medicine.setCubicMetersOxygen(line[8]);
      return medicine;
    } catch (Exception e) {
      throw new RuntimeException("Error creating Medicine", e);
    }

  }


}
