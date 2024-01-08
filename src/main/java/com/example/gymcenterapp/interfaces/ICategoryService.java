package com.example.gymcenterapp.interfaces;

import com.example.gymcenterapp.entities.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICategoryService
{
    Category addCategoryWithOneImage( Category category, MultipartFile[] file);

    Category addImagesToCategory( Long catId, MultipartFile[] files );

    List<Category> retrieveAllCategories();

    Category retrieveCategory(Long id);

    void deleteCategory(Long id);

    Category updateCategoryData(Long id, Category category);

    Category updateCategory( Category category, MultipartFile[] file);
}
