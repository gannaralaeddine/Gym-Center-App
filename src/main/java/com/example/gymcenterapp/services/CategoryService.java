package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.Category;
import com.example.gymcenterapp.entities.ImageModel;
import com.example.gymcenterapp.interfaces.ICategoryService;
import com.example.gymcenterapp.repositories.CategoryRepository;
import com.example.gymcenterapp.repositories.ImageModelRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class CategoryService implements ICategoryService
{


//    final String directory = "http://localhost:8089/categories/";

    @Value("${app.directory}")
    private String directory;

    private final CategoryRepository categoryRepository;
    private final ImageModelService imageModelService;
    private final ImageModelRepository imageModelRepository;


    public CategoryService(CategoryRepository categoryRepository, ImageModelService imageModelService, ImageModelRepository imageModelRepository) {
        this.categoryRepository = categoryRepository;
        this.imageModelService = imageModelService;
        this.imageModelRepository = imageModelRepository;
    }

    @Override
    public Category addCategoryWithOneImage( Category category, MultipartFile[] file)
    {
        String[] imageType = Objects.requireNonNull(file[0].getContentType()).split("/");
        String uniqueName = imageModelService.generateUniqueName() + "." + imageType[1];
        String filePath = directory + "categories/";

        try
        {
            String currentDirectory = System.getProperty("user.dir");
            System.out.println("currentDirectory: " + currentDirectory);

            ImageModel imageModel = new  ImageModel();
            imageModel.setImageName( uniqueName );
            imageModel.setImageType( file[0].getContentType() );
            imageModel.setImageSize( file[0].getSize() );
            imageModel.setImageUrl( filePath + uniqueName );

            HashSet<ImageModel> images = new HashSet<>();
            images.add(imageModel);

            category.setCatImage(uniqueName);
            category.setImages(images);
            File fileDirectory = new File(directory);
            if ( !fileDirectory.exists() )
            {
                fileDirectory.mkdirs();
            }
            file[0].transferTo(new File(directory, uniqueName));

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

            String[] imageType = Objects.requireNonNull(file[0].getContentType()).split("/");
            String uniqueName = imageModelService.generateUniqueName() + "." + imageType[1];
            String filePath = directory + "categories\\" + uniqueName;

            try
            {
                ImageModel imageModel = new ImageModel();
                imageModel.setImageName( uniqueName );
                imageModel.setImageType(file[0].getContentType());
                imageModel.setImageSize(file[0].getSize());
                imageModel.setImageUrl(filePath);

                Set<ImageModel> images = existingCategory.getImages();
                images.add(imageModel);

                file[0].transferTo(new File(filePath));

                ImageModel existingImageModel = imageModelService.findImageByName(existingCategory.getCatImage());

                images.remove(existingImageModel);
                imageModelRepository.delete(existingImageModel);
                imageModelService.removeFile(directory + "categories\\", existingCategory.getCatImage());


                existingCategory.setCatImage( uniqueName );
                existingCategory.setImages(images);


                return categoryRepository.save(existingCategory);
            }
            catch (Exception e)
            {
                System.out.println("Error in update category with image: " + e.getMessage());
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
            Set<ImageModel> images =  imageModelService.prepareFiles(files, category.getImages(), directory + "categories\\");

            category.setImages(images);

            return categoryRepository.save(category);
        }
        catch (Exception e)
        {
            System.out.println("Error in add Images To Category: " + e.getMessage());
            return null;
        }
    }

    public Category addCategoryWithOneImage(Category category)
    {
        return categoryRepository.save(category);
    }
    
}
