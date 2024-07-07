package com.example.gymcenterapp;

import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.gymcenterapp.entities.Category;
import com.example.gymcenterapp.services.CategoryService;


@SpringBootTest
public class CategoryServiceTest 
{
    @Autowired
    private CategoryService categoryService;


    @Test
    public void addCategory() 
    {
        Category category = categoryService.addCategoryWithOneImage(new Category(null, "self-defense", "description", null, null, null));
        assertNotNull(category);
        categoryService.deleteCategory(category.getCatId());
    }

    @Test
    public void retrieveAllCategories() { assertNotNull(categoryService.retrieveAllCategories()); }

    @Test
    public void retrieveCategory() 
    { 
        Category category = categoryService.addCategoryWithOneImage(new Category(null, "self-defense", "description", null, null, null));
        assertNotNull(categoryService.retrieveCategory(category.getCatId()));
        categoryService.deleteCategory(category.getCatId());
    }

    @Test
    public void updateCategory()
    {
        Category category = categoryService.addCategoryWithOneImage(new Category(null, "self-defense", "description", null, null, null));
        assertNotNull(category);
        assertNotNull(categoryService.updateCategoryData(category.getCatId(), new Category(null, "dance", "description", null, null, null)));
        categoryService.deleteCategory(category.getCatId());
    }

    @Test
    public void deleteCategory()
    {
        addCategory();
    }
}
