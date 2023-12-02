package com.ivanovvasil.CapstoneB.ASL.exemption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
