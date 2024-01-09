package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.Category;
import com.example.gymcenterapp.entities.ImageModel;
import com.example.gymcenterapp.interfaces.ICategoryService;
import com.example.gymcenterapp.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor

public class CategoryService implements ICategoryService
{

    final String directory = "C:\\Users\\ganna\\IdeaProjects\\Gym-Center-App\\src\\main\\resources\\static\\categories\\";

    CategoryRepository categoryRepository;

    ImageModelService imageModelService;


    @Override
    public Category addCategoryWithOneImage( Category category, MultipartFile[] file)
    {
        String[] imageType = file[0].getContentType().split("/");
        String uniqueName = imageModelService.generateUniqueName() + "." + imageType[1];
        String filePath = directory + uniqueName;

        try
        {
            ImageModel imageModel = new  ImageModel();
            imageModel.setImageName( uniqueName );
            imageModel.setImageType( file[0].getContentType() );
            imageModel.setImageSize( file[0].getSize() );
            imageModel.setImageUrl( filePath );

            HashSet<ImageModel> images = new HashSet<>();
            images.add(imageModel);

            category.setCatImage(uniqueName);
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
    public Category updateCategoryData(Long id, Category category)
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
    public Category updateCategory( Category category, MultipartFile[] file)
    {
        Category existingCategory = categoryRepository.findById(category.getCatId()).orElse(null);

        if (existingCategory != null)
        {
            existingCategory.setCatName(category.getCatName());
            existingCategory.setCatDescription(category.getCatDescription());

            String[] imageType = file[0].getContentType().split("/");
            String uniqueName = imageModelService.generateUniqueName() + "." + imageType[1];
            String filePath = directory + uniqueName;

            try
            {
                ImageModel imageModel = new ImageModel();
                imageModel.setImageName( uniqueName );
                imageModel.setImageType(file[0].getContentType());
                imageModel.setImageSize(file[0].getSize());
                imageModel.setImageUrl(filePath);

                HashSet<ImageModel> images = new HashSet<>();
                images.add(imageModel);

                imageModelService.removeFile(directory, existingCategory.getCatImage());
                deleteImageFromDataBase(category);

                existingCategory.setCatImage( uniqueName );
                existingCategory.setImages(images);

                file[0].transferTo(new File(filePath));

                return categoryRepository.save(existingCategory);
            }
            catch (Exception e)
            {
                System.out.println("Error in update category: " + e.getMessage());
                return null;
            }

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
            Set<ImageModel> images =  imageModelService.prepareFiles(files, category.getImages(), directory);

            category.setImages(images);

            return categoryRepository.save(category);
        }
        catch (Exception e)
        {
            System.out.println("Error in add Images To Category: " + e.getMessage());
            return null;
        }
    }


    @Transactional
    public void deleteImageFromDataBase(Category category)
    {
        Category existingCategory = categoryRepository.findById(category.getCatId()).orElse(null);

        if (existingCategory != null)
        {
            ImageModel imageModel = imageModelService.findImageByName(existingCategory.getCatImage());

            existingCategory.getImages().remove(imageModel);


            categoryRepository.save(existingCategory);


        }
    }
}
