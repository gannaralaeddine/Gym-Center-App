package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.Category;
import com.example.gymcenterapp.entities.ImageModel;
import com.example.gymcenterapp.interfaces.ICategoryService;
import com.example.gymcenterapp.repositories.CategoryRepository;
import com.example.gymcenterapp.repositories.ImageModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor

public class CategoryService implements ICategoryService
{

    final String directory = "C:\\Users\\ganna\\IdeaProjects\\Gym-Center-App\\src\\main\\resources\\static\\categories\\";

    CategoryRepository categoryRepository;

    ImageModelRepository imageModelRepository;


    @Override
    public Category addCategoryWithOneImage( Category category, MultipartFile[] file)
    {
        String filePath = directory+file[0].getOriginalFilename();

        try
        {
            ImageModel imageModel = new  ImageModel();
            imageModel.setImageName( file[0].getOriginalFilename() );
            imageModel.setImageType( file[0].getContentType() );
            imageModel.setImageSize( file[0].getSize() );
            imageModel.setImageUrl( filePath );

            HashSet<ImageModel> images = new HashSet<>();
            images.add(imageModel);

            category.setCatImage(file[0].getOriginalFilename());
            category.setImages(images);

            file[0].transferTo(new File(filePath));

            return categoryRepository.save(category);
        }
        catch (Exception e)
        {
            System.out.println("Error in create category: " + e.getMessage());
            return null;
        }

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


    @Override
    public Category addImagesToCategory( Long catId, MultipartFile[] files )
    {
        try
        {
            Category category = categoryRepository.findById(catId).orElse(null);

            assert category != null;
            Set<ImageModel> images =  prepareFiles(files, category.getImages());

            category.setImages(images);

            return categoryRepository.save(category);
        }
        catch (Exception e)
        {
            System.out.println("Error in add Images To Category: " + e.getMessage());
            return null;
        }
    }

    public Set<ImageModel> prepareFiles(MultipartFile[] files, Set<ImageModel> images) throws IOException {


        for (MultipartFile file: files)
        {
            String filePath = directory+file.getOriginalFilename();

            ImageModel imageModel = new ImageModel();
            imageModel.setImageName(file.getOriginalFilename());
            imageModel.setImageType(file.getContentType());
            imageModel.setImageSize(file.getSize());
            imageModel.setImageUrl(file.getOriginalFilename());

            images.add(imageModel);

            file.transferTo(new File(filePath));
        }

        return images;
    }

}
