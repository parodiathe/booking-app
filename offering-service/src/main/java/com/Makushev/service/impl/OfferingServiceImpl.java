package com.Makushev.service.impl;

import com.Makushev.model.Offering;
import com.Makushev.payload.CategoryDTO;
import com.Makushev.payload.SalonDTO;
import com.Makushev.payload.ServiceDTO;
import com.Makushev.repository.OfferingRepository;
import com.Makushev.service.OfferingService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Data
@RequiredArgsConstructor
public class OfferingServiceImpl implements OfferingService {

    private final OfferingRepository offeringRepository;

    @Override
    public Offering createService(SalonDTO salonDTO,
                                         ServiceDTO serviceDTO,
                                         CategoryDTO categoryDTO) {
        Offering offering = new Offering();

        offering.setImage(serviceDTO.getImage());
        offering.setSalonId(salonDTO.getId());
        offering.setName(serviceDTO.getName());
        offering.setDescription(serviceDTO.getDescription());
        offering.setCategoryId(categoryDTO.getId());
        offering.setPrice(serviceDTO.getPrice());
        offering.setDuration(serviceDTO.getDuration());

        return offeringRepository.save(offering);
    }

    @Override
    public Offering updateService(Long serviceId,
                                  Offering service) throws Exception {
        Offering offering = offeringRepository
                .findById(serviceId).orElse(null);

        if(offering==null){
            throw new Exception("service not exist with id " + serviceId);
        }

        offering.setImage(service.getImage());
        offering.setName(service.getName());
        offering.setDescription(service.getDescription());
        offering.setPrice(service.getPrice());
        offering.setDuration(service.getDuration());

        return offeringRepository.save(offering);
    }

    @Override
    public Set<Offering> getAllServiceBySalonId(Long salonId, Long categoryId) {
        Set<Offering> services = offeringRepository.findBySalonId(salonId);
        if(categoryId!=null){
            services = services.stream().filter((service) -> service.getCategoryId() != null &&
                    service.getCategoryId()==categoryId).collect(Collectors.toSet());
        }
        return services;
    }

    @Override
    public Set<Offering> getServiceByIds(Set<Long> ids) {
        List<Offering> services = offeringRepository.findAllById(ids);
        return new HashSet<>(services);
    }

    @Override
    public Offering getServiceById(Long id) throws Exception {
        Offering offering = offeringRepository
                .findById(id).orElse(null);

        if(offering==null){
            throw new Exception("service not exist with id " + id);
        }
        return offering;
    }
}
