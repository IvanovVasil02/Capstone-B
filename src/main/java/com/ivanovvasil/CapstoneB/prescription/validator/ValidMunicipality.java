package com.ivanovvasil.CapstoneB.prescription.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MunicipalityValidator.class)
public @interface ValidMunicipality {
  String message() default "The value entered is invalid";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
  
}
