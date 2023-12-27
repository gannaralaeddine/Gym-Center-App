package com.example.gymcenterapp.interfaces;

import com.example.gymcenterapp.entities.Category;
import com.example.gymcenterapp.entities.ImageData;
import com.example.gymcenterapp.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ICategoryService
{

    Category addCategory(Category category);

    ImageData addCategoryWithImage(Category category, MultipartFile file) throws IOException;

    List<Category> retrieveAllCategories();

    Category retrieveCategory(Long id);

    void deleteCategory(Long id);

    Category updateCategory(Long id, Category category);

    ImageData uploadImage(MultipartFile image, Category category) throws IOException;
}
