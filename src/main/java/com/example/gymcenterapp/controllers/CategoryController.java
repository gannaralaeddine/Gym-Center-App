package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.Category;
import com.example.gymcenterapp.entities.ImageModel;
import com.example.gymcenterapp.services.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping("/category")
@AllArgsConstructor
@CrossOrigin(origins = "")
public class CategoryController
{
    final String directory = "C:\\Users\\ganna\\IdeaProjects\\Gym-Center-App\\src\\main\\resources\\static\\categories\\";


    CategoryService categoryService;


    @GetMapping("/retrieve-all-categories")
    @ResponseBody
    public List<Category> getAllCategories() { return categoryService.retrieveAllCategories(); }

    @GetMapping("/retrieve-category/{id}")
    @ResponseBody
    public Category retrieveCategory(@PathVariable("id") Long idCategory) { return categoryService.retrieveCategory(idCategory);}


    @PostMapping(value = "/add-category")
    @ResponseBody
    public Category addCategory(@RequestBody Category category)  {

        return categoryService.addCategory(category);
    }

    @PutMapping(value= "/update-category/{id}")
    @ResponseBody
    public Category updateCategory(@PathVariable("id") Long idCategory, @RequestBody Category category) { return categoryService.updateCategory(idCategory,category); }

    @DeleteMapping(value = "/delete-category/{id}")
    public void deleteCategory(@PathVariable("id") Long idCategory) { categoryService.deleteCategory(idCategory); }



// Get category image
//--------------------------------------------------------------------------------------------------------------------------

    @GetMapping("/get-image/{image-name}")
    public ResponseEntity<?> getImage(@PathVariable("image-name") String imageName) throws IOException {

        byte[] imageData = categoryService.getImage(imageName);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
    }


// Add Category with many images
//--------------------------------------------------------------------------------------------------------------------------

    @PostMapping(value = { "/create-category" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Category createCategory(@RequestPart("catName") String catName,
                                   @RequestPart("catDescription") String catDescription,
                                   @RequestPart("imageFile") MultipartFile[] file)
    {

        try
        {
            Set<ImageModel> images =  prepareFiles(file);
            Category category = new Category();
            category.setCatName(catName);
            category.setCatDescription(catDescription);
            category.setImages(images);


            return categoryService.addCategory(category);
        }
        catch (Exception e)
        {
            System.out.println("Error in create category: " + e.getMessage());
            return null;
        }

    }

    public Set<ImageModel> prepareFiles(MultipartFile[] files) throws IOException {
        Set<ImageModel> images = new HashSet<>();

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



// Add Category with one image
//--------------------------------------------------------------------------------------------------------------------------


    @PostMapping(value = { "/create-one-category" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Category createOneCategory(@RequestPart("catName") String catName,
                                   @RequestPart("catDescription") String catDescription,
                                   @RequestPart("imageFile") MultipartFile[] file)
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

            Category category = new Category();
            category.setCatName(catName);
            category.setCatDescription(catDescription);
            category.setCatImage(file[0].getOriginalFilename());
            category.setImages(images);

            file[0].transferTo(new File(filePath));

            return categoryService.addCategory(category);
        }
        catch (Exception e)
        {
            System.out.println("Error in create category: " + e.getMessage());
            return null;
        }

    }
}
