package com.Makushev.controller;

import com.Makushev.model.Offering;
import com.Makushev.service.OfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/offering")
@RequiredArgsConstructor
public class OfferingController {

    private final OfferingService offeringService;

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<Set<Offering>> getServicesBySalonId(
            @PathVariable Long salonId,
            @RequestParam(required = false) Long categoryId)
    {
        Set<Offering> offerings = offeringService.getAllServiceBySalonId(salonId, categoryId);
        return ResponseEntity.ok(offerings);
    }

    @GetMapping("{id}")
    public ResponseEntity<Offering> getServiceById(
            @PathVariable Long id) throws Exception {
        Offering offering = offeringService.getServiceById(id);
        return ResponseEntity.ok(offering);
    }

    @GetMapping("/list/{ids}")
    public ResponseEntity<Set<Offering>> getServicesByIds(
            @PathVariable Set<Long> ids)
    {
        Set<Offering> offerings = offeringService.getServiceByIds(ids);
        return ResponseEntity.ok(offerings);
    }
}
