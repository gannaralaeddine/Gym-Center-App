package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.Category;
import com.example.gymcenterapp.entities.ImageModel;
import com.example.gymcenterapp.interfaces.ICategoryService;
import com.example.gymcenterapp.repositories.CategoryRepository;
import com.example.gymcenterapp.repositories.ImageModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class CategoryService implements ICategoryService
{

    final String directory = "C:\\Users\\ganna\\IdeaProjects\\Gym-Center-App\\src\\main\\resources\\static\\categories\\";

    CategoryRepository categoryRepository;

    ImageModelRepository imageModelRepository;

    @Override
    public Category addCategory(Category category)
    {
        return categoryRepository.save(category);
    }


    @Override
    public List<Category> retrieveAllCategories() { return categoryRepository.findAll(); }

    @Override
    public Category retrieveCategory(Long id) { return categoryRepository.findById(id).orElse(null); }

    @Override
    public void deleteCategory(Long id)
    {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category updateCategory(Long id, Category category)
    {
        Category existingCategory = categoryRepository.findById(id).orElse(null);

        if (existingCategory != null)
        {
            existingCategory.setCatName(category.getCatName());
            existingCategory.setCatDescription(category.getCatDescription());
            return categoryRepository.save(existingCategory);
        }

        return null;
    }


    public byte[] getImage(String imageName) throws IOException {
        Optional<ImageModel> imageModel = imageModelRepository.findByName(imageName);

        if (imageModel.isPresent())
        {
            String filePath = imageModel.get().getImageUrl();
            return Files.readAllBytes(new File(filePath).toPath());
        }
        else
        {
            return null;
        }

    }

}
