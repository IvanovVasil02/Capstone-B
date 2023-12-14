package com.ivanovvasil.CapstoneB.Medicine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicines")
public class MedicinesController {

  @Autowired
  MedicinesService ms;

  @GetMapping("")
  public Page<Medicine> getMedicines(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "30") int size,
                                     @RequestParam(defaultValue = "id") String orderBy) {
    return ms.getAllMedicines(page, size, orderBy);
  }

  @GetMapping("/searchByActiveIngredient")
  public Page<MedicineDTO> SearchMedicineByActiveIngredient(@RequestParam String search,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "30") int size,
                                                            @RequestParam(defaultValue = "id") String orderBy) {
    return ms.getSearchedMedicineByActiveIngredient(search, page, size, orderBy);
  }

  @GetMapping("/searchByName")
  public Page<MedicineDTO> SearchMedicineByName(@RequestParam String search,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "30") int size,
                                                @RequestParam(defaultValue = "id") String orderBy) {
    return ms.getSearchedMedicineByName(search, page, size, orderBy);
  }
}
