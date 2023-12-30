package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.Category;
import com.example.gymcenterapp.services.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/category")
@AllArgsConstructor
@CrossOrigin(origins = "")
public class CategoryController
{

    CategoryService categoryService;


    @GetMapping("/retrieve-all-categories")
    @ResponseBody
    public List<Category> getAllCategories() { return categoryService.retrieveAllCategories(); }

    @GetMapping("/retrieve-category/{id}")
    @ResponseBody
    public Category retrieveCategory(@PathVariable("id") Long idCategory) { return categoryService.retrieveCategory(idCategory);}


    @PutMapping(value= "/update-category/{id}")
    @ResponseBody
    public Category updateCategory(@PathVariable("id") Long idCategory, @RequestBody Category category) {
        return categoryService.updateCategory(idCategory, category);
    }

    @DeleteMapping(value = "/delete-category/{id}")
    public void deleteCategory(@PathVariable("id") Long idCategory) { categoryService.deleteCategory(idCategory); }



// Get category image
//--------------------------------------------------------------------------------------------------------------------------

    @GetMapping("/get-image/{image-name}")
    public ResponseEntity<?> getImageByName(@PathVariable("image-name") String imageName) throws IOException {

        byte[] imageData = categoryService.getImage(imageName);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
    }


// Add Category with one image
//--------------------------------------------------------------------------------------------------------------------------

    @PostMapping(value = { "/create-category" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Category addCategoryWithOneImage(@RequestPart("category") Category category,
                                   @RequestPart("imageFile") MultipartFile[] images)
    {
        return categoryService.addCategoryWithOneImage(category, images);
    }


// Add Images to Category
//--------------------------------------------------------------------------------------------------------------------------

    @PutMapping(value = { "/add-images-to-category" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Category addImagesToCategory(@RequestPart("category") Category category,
                                        @RequestPart("imageFile") MultipartFile[] images)
    {
        return categoryService.addImagesToCategory(category.getCatId(), images);
    }
}
