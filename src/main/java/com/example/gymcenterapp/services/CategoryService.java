package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.Category;
import com.example.gymcenterapp.interfaces.ICategoryService;
import com.example.gymcenterapp.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor

public class CategoryService implements ICategoryService
{
    CategoryRepository categoryRepository;


    @Override
    public Category addCategory(Category category) { return categoryRepository.save(category); }

    @Override
    public List<Category> retrieveAllCategories() { return categoryRepository.findAll(); }

    @Override
    public Category retrieveCategory(Long id) { return categoryRepository.findById(id).orElse(null); }

    @Override
    public void deleteCategory(Long id) { categoryRepository.deleteById(id);}

    @Override
    public Category updateCategory(Long id, Category category)
    {
        Category existingCategory = categoryRepository.findById(id).orElse(null);

        if (existingCategory != null)
        {
            existingCategory.setCatName(category.getCatName());
            existingCategory.setCatDescription(category.getCatDescription());
            existingCategory.setCatImage(category.getCatImage());
            return categoryRepository.save(existingCategory);
        }

        return null;
    }
}
