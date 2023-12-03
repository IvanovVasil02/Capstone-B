package com.ivanovvasil.CapstoneB.ASL.ASLCodes;

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
public class ASLService {
  @Autowired
  ASLDataRepo ar;

  public void readASLFileCsv(String path) throws IOException {
    if (ar.findAll().isEmpty()) {
      try {
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        CSVReader reader = new CSVReaderBuilder(new FileReader(path)).withCSVParser(parser).withSkipLines(1).build();
        List<String[]> ASLRows = reader.readAll();
        for (String[] ASLRow : ASLRows) {
          ASL asl = this.createASL(ASLRow);
          ar.save(asl);
        }
      } catch (CsvException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private ASL createASL(String[] line) {
    try {
      ASL asl = new ASL();
      asl.setRegionCode(line[1]);
      asl.setRegionDenomination(line[2]);
      asl.setCompanyCode(line[3]);
      asl.setCompanyDenomination(line[4]);
      asl.setMunicipalityCode(line[5]);
      asl.setMunicipalityDenomination(line[6]);
      return asl;
    } catch (Exception e) {
      throw new RuntimeException("Error creating ASL", e);
    }
  }

  public String getAslCodeByRegionName(String region) {
    ASL aslCode = ar.findByRegionLike(region);
    return aslCode.getCompanyCode();
  }
}
