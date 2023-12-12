package com.ivanovvasil.CapstoneB.municipality;

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

@Service
public class MunicipalityService {
  @Autowired
  MunicipalityRepo mr;

  public List<Municipality> getAll() {
    return mr.findAll();
  }

  public void readMunicipalityFileCsv(String path) throws IOException {
    if (mr.findAll().isEmpty()) {
      try {
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        CSVReader reader = new CSVReaderBuilder(new FileReader(path)).withCSVParser(parser).withSkipLines(1).build();
        List<String[]> MunicipalityRows = reader.readAll();
        for (String[] MunicipalityRow : MunicipalityRows) {
          Municipality municipality = this.createMunicipality(MunicipalityRow);
          mr.save(municipality);
        }
      } catch (CsvException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private Municipality createMunicipality(String[] line) {
    try {
      Municipality municipality = new Municipality();
      municipality.setMunicipality(line[9]);
      municipality.setCap(line[5]);
      municipality.setRegion(line[3]);
      municipality.setProvince(line[2]);
      return municipality;
    } catch (Exception e) {
      throw new RuntimeException("Error creating Municipality", e);
    }
  }

  public Boolean existMunicipality(String code) {
    return mr.existsMunicipalityByCap(code);
  }

}
