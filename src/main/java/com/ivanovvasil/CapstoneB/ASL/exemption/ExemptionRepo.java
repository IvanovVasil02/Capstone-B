package com.ivanovvasil.CapstoneB.ASL.exemption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExemptionRepo extends JpaRepository<Exemption, UUID> {
}
