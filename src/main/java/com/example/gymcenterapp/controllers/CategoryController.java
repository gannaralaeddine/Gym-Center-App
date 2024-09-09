package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.Category;
import com.example.gymcenterapp.services.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
@CrossOrigin(origins = "")
public class CategoryController
{

    CategoryService categoryService;


    @GetMapping("/retrieve-all-categories")
    @ResponseBody
//    @RolesAllowed( "ROLE_USER" )
//    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public List<Category> getAllCategories() { return categoryService.retrieveAllCategories(); }

    @GetMapping("/retrieve-category/{id}")
    @ResponseBody
    public Category retrieveCategory(@PathVariable("id") Long idCategory) { return categoryService.retrieveCategory(idCategory);}


    @PutMapping(value= "/update-category/{id}")
    @ResponseBody
    public Category updateCategoryData(@PathVariable("id") Long idCategory, @RequestBody Category category) {
        return categoryService.updateCategoryData(idCategory, category);
    }

    @DeleteMapping(value = "/delete-category/{id}")
    public void deleteCategory(@PathVariable("id") Long idCategory) 
    { 
        System.out.println("categoryId: " + idCategory);
        categoryService.deleteCategory(idCategory); 
    }


// Add Category with one image
//----------------------------------------------------------------------------------------------------------------------

    @PostMapping(value = { "/create-category" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Category addCategoryWithOneImage(@RequestPart("category") Category category,
                                   @RequestPart("imageFile") MultipartFile[] images)
    {
        return categoryService.addCategoryWithOneImage(category, images);
    }


// Add Images to Category
//----------------------------------------------------------------------------------------------------------------------

    @PutMapping(value = { "/add-images-to-category" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Category addImagesToCategory(@RequestPart("category") Category category,
                                        @RequestPart("imageFile") MultipartFile[] images)
    {
        return categoryService.addImagesToCategory(category.getCatId(), images);
    }


// Update Category
//----------------------------------------------------------------------------------------------------------------------
    @PutMapping(value = { "/update-category" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Category updateCategory(@RequestPart("category") Category category,
                                   @RequestPart("imageFile") MultipartFile[] images)
    {
        return categoryService.updateCategory(category, images);
    }

}
