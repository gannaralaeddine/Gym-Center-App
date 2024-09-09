package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.services.ImageModelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
@RequestMapping("/image")
@AllArgsConstructor
public class ImageController
{

    ImageModelService imageModelService;

// Get category image
//--------------------------------------------------------------------------------------------------------------------------

    @GetMapping("/get-image/{image-name}")
    public ResponseEntity<?> getImageByName(@PathVariable("image-name") String imageName) throws IOException
    {
        byte[] imageData = imageModelService.getImage(imageName);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
    }
}
