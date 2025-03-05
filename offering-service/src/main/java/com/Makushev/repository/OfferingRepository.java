package com.Makushev.repository;

import com.Makushev.model.Offering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OfferingRepository extends JpaRepository<Offering, Long> {

    Set<Offering> findBySalonId(Long salonId);

}
