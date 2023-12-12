package com.ivanovvasil.CapstoneB.prescription.validator;

import com.ivanovvasil.CapstoneB.municipality.MunicipalityRepo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class MunicipalityValidator implements ConstraintValidator<ValidMunicipality, String> {

  @Autowired
  MunicipalityRepo mr;


  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return false;
    } else {
      return mr.existsMunicipalityByCap(value);
    }
  }
}
