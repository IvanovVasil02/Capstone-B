package com.ivanovvasil.CapstoneB.Medicine;

import com.ivanovvasil.CapstoneB.exceptions.NotFoundException;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class MedicinesService {

  @Autowired
  private MedicineRepo mr;

  public void save(Medicine province) {
    mr.save(province);
  }

  public Medicine findById(UUID id) {
    return mr.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public List<Medicine> getAllMedicines() {
    return mr.findAll();
  }

  public Page<Medicine> getAllMedicines(int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    return mr.findAll(pageable);
  }

  public void readMedicineFileCsv(String path) throws IOException {
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
      medicine.setMedicineName(line[2]);
      medicine.setPublicPrice(line[3]);
      medicine.setHolderOfMarketingAuthorization(line[4]);
      medicine.setIdentificationCode(line[5]);
      medicine.setXInAifaTransparencyList(line[6]);
      medicine.setXInRegionList(line[7]);
      medicine.setCubicMetersOxygen(line[8]);
      return medicine;
    } catch (Exception e) {
      throw new RuntimeException("Error creating Medicine", e);
    }

  }


  public Page<MedicineDTO> getSearchedMedicineByActiveIngredient(String search, int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    Page<Medicine> medicinePage = mr.findByActiveIngredientIgnoreCaseContaining(search, pageable);
    return medicinePage.map(this::convertMedicineToDTO);
  }

  public Page<MedicineDTO> getSearchedMedicineByMedicineName(String search, int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    Page<Medicine> medicinePage = mr.findByMedicineNameContainingIgnoreCase(search, pageable);
    return medicinePage.map(this::convertMedicineToDTO);
  }

  public MedicineDTO convertMedicineToDTO(Medicine medicine) {
    return MedicineDTO
            .builder()
            .medicineId(medicine.getId())
            .nameAndPackaging(medicine.getMedicineName())
            .activeIngredient(medicine.getActiveIngredient())
            .holderOfMarketingAuthorization(medicine.getHolderOfMarketingAuthorization())
            .identificationCode(medicine.getIdentificationCode())
            .publicPrice(medicine.getPublicPrice())
            .build();
  }
}
