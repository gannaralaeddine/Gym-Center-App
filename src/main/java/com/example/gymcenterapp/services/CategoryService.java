package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.Category;
import com.example.gymcenterapp.entities.ImageData;
import com.example.gymcenterapp.interfaces.ICategoryService;
import com.example.gymcenterapp.repositories.CategoryRepository;
import com.example.gymcenterapp.repositories.ImageDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class CategoryService implements ICategoryService
{

    final String directory = "C:\\Users\\ganna\\IdeaProjects\\Gym-Center-App\\src\\main\\resources\\static\\categories\\";

    CategoryRepository categoryRepository;

    ImageDataRepository imageDataRepository;

    @Override
    public Category addCategory(Category category)
    {
        return categoryRepository.save(category);
    }

    @Override
    public ImageData addCategoryWithImage(Category category, MultipartFile file) throws IOException {

        Category cat = categoryRepository.save(category);

        return uploadImage(file, cat);
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

    @Override
    public ImageData uploadImage(MultipartFile image, Category category) throws IOException {

        String filePath = directory+image.getOriginalFilename();


        ImageData imageData = ImageData.builder()
                .imageName(image.getOriginalFilename())
                .imageType(image.getContentType())
                .imageSize((image.getSize()))
                .imageUrl(filePath)
                .build();

        image.transferTo(new File(filePath));

        imageData.setImageCategory(category);

        category.setCatImage(filePath);
        categoryRepository.save(category);

        return imageDataRepository.save(imageData);
    }

    public byte[] getImage(String imageName) throws IOException {
        Optional<ImageData> imageData = imageDataRepository.findByName(imageName);

        String filePath = imageData.get().getImageUrl();

        byte[] image = Files.readAllBytes(new File(filePath).toPath());

        return image;
    }

}
