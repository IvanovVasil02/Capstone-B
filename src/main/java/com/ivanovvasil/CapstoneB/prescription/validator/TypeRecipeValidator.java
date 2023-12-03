package com.ivanovvasil.CapstoneB.prescription.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TypeRecipeValidator implements ConstraintValidator<ValidTypeRecipe, String> {
  private Enum<?>[] enums;

  @Override
  public void initialize(ValidTypeRecipe constraintAnnotation) {
    enums = constraintAnnotation.enumClass().getEnumConstants();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value != null) {
      for (Enum<?> enumValue : enums) {
        if (enumValue.name().equals(value)) {
          return true;
        }
      }
    }
    for (Enum<?> enumValue : enums) {
      if (enumValue.name().equals(value)) {
        return true;
      }
    }
    return false;
  }
}

