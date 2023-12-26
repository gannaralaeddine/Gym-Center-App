package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.Category;
import com.example.gymcenterapp.entities.ImageData;
import com.example.gymcenterapp.repositories.ImageDataRepository;
import com.example.gymcenterapp.services.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/category")
@AllArgsConstructor

public class CategoryController
{
    CategoryService categoryService;

    ImageDataRepository imageDataRepository;

    @GetMapping("/retrieve-all-categories")
    @ResponseBody
    public List<Category> getAllCategories() { return categoryService.retrieveAllCategories(); }

    @GetMapping("/retrieve-category/{id}")
    @ResponseBody
    public Category retrieveCategory(@PathVariable("id") Long idCategory) { return categoryService.retrieveCategory(idCategory);}
    
    @PostMapping(value = "/add-category")
    @ResponseBody
    public ImageData addCategory(@RequestParam("catImage") String catImage,
                                @RequestParam("catName") String catName,
                                @RequestParam("catDescription") String catDescription,
                                @RequestParam("file")MultipartFile file) throws IOException {

        Category category = new Category();
        category.setCatName(catName);
        category.setCatDescription(catDescription);

        return categoryService.addCategory(category, file);
    }

    @PutMapping(value= "/update-category/{id}")
    @ResponseBody
    public Category updateCategory(@PathVariable("id") Long idCategory, @RequestBody Category category) { return categoryService.updateCategory(idCategory,category); }

    @DeleteMapping(value = "/delete-category/{id}")
    public void deleteCategory(@PathVariable("id") Long idCategory) { categoryService.deleteCategory(idCategory); }


    @PostMapping("/upload-image")
    public ImageData uploadImage(@RequestBody ImageData data) throws IOException {
        return imageDataRepository.save(data);
    }
}
