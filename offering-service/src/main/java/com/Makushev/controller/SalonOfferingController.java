package com.Makushev.controller;

import com.Makushev.model.Offering;
import com.Makushev.payload.CategoryDTO;
import com.Makushev.payload.SalonDTO;
import com.Makushev.payload.ServiceDTO;
import com.Makushev.service.OfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/offering/salon-owner")
@RequiredArgsConstructor
public class SalonOfferingController {

    private final OfferingService offeringService;

    @PostMapping
    public ResponseEntity<Offering> createService(
            @RequestBody ServiceDTO serviceDTO)
    {
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(serviceDTO.getCategory());

        Offering offerings = offeringService.createService(salonDTO, serviceDTO, categoryDTO);

        return ResponseEntity.ok(offerings);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Offering> updateService(
            @PathVariable Long id,
            @RequestBody Offering offering) throws Exception {

        Offering offerings = offeringService.updateService(id, offering);

        return ResponseEntity.ok(offerings);
    }

}
