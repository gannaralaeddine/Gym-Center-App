package com.example.gymcenterapp.interfaces;

import com.example.gymcenterapp.entities.Category;

import java.util.List;

public interface ICategoryService
{

    Category addCategory(Category category);

    List<Category> retrieveAllCategories();

    Category retrieveCategory(Long id);

    void deleteCategory(Long id);

    Category updateCategory(Long id, Category category);

}
