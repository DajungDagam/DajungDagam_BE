package com.dajungdagam.dg.service;

import com.dajungdagam.dg.domain.entity.ItemCategory;
import com.dajungdagam.dg.repository.ItemCategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCategoryService {
    private ItemCategoryRepository itemCategoryRepository;

    public ItemCategoryService(ItemCategoryRepository itemCategoryRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
    }

    @Transactional
    public List<ItemCategory> getAllCategories() {
        return itemCategoryRepository.findAll();
    }
}
