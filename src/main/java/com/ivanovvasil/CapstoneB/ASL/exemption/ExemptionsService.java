package com.ivanovvasil.CapstoneB.ASL.exemption;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ExemptionsService {
  @Autowired
  ExemptionRepo er;

  public void save(Exemption exemption) {
    er.save(exemption);
  }

  public void findById(UUID id) {
    er.findById(id);
  }

  public void deleteExemption(Exemption exemption) {
    er.delete(exemption);
  }

  public void readExemptionFileCsv(String path) throws IOException {
    if (er.findAll().isEmpty()) {
      try {
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        CSVReader reader = new CSVReaderBuilder(new FileReader(path)).withCSVParser(parser).withSkipLines(1).build();
        List<String[]> ExemptionRows = reader.readAll();
        for (String[] ExemptionRow : ExemptionRows) {
          Exemption exemption = this.createExemption(ExemptionRow);
          er.save(exemption);
        }
      } catch (CsvException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private Exemption createExemption(String[] line) {
    try {
      Exemption exemption = new Exemption();
      exemption.setExemptionCode(line[0]);
      exemption.setDescription(line[1]);
      exemption.setType(line[2]);
      return exemption;
    } catch (Exception e) {
      throw new RuntimeException("Error creating Exemption", e);
    }
  }
}
