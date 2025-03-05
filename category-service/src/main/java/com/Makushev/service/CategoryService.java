package com.Makushev.service;

import com.Makushev.dto.SalonDTO;
import com.Makushev.model.Category;

import java.util.Set;

public interface CategoryService {

    Category saveCategory(Category category, SalonDTO salonDTO);

    Set<Category> getAllCategoriesBySalon(Long id);

    Category getCategoryById(Long id) throws Exception;

    void deleteCategoryById(Long Id, Long salonId) throws Exception;

}
