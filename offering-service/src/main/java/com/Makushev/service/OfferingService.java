package com.Makushev.service;

import com.Makushev.model.Offering;
import com.Makushev.payload.CategoryDTO;
import com.Makushev.payload.SalonDTO;
import com.Makushev.payload.ServiceDTO;

import java.util.Set;

public interface OfferingService {

    Offering createService(SalonDTO salonDTO,
                           ServiceDTO serviceDTO,
                           CategoryDTO categoryDTO);

    Offering updateService(Long serviceId,
                                  Offering service) throws Exception;

    Set<Offering> getAllServiceBySalonId(Long salonId,
                                                Long categoryId);

    Set<Offering> getServiceByIds(Set<Long> ids);

    Offering getServiceById(Long id) throws Exception;


}
