package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.ImageModel;
import com.example.gymcenterapp.repositories.ImageModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageModelService
{

    ImageModelRepository imageModelRepository;

    public byte[] getImage(String imageName) throws IOException {
        ImageModel imageModel = imageModelRepository.findByName(imageName);

        if (imageModel != null)
        {
            String filePath = imageModel.getImageUrl();
            return Files.readAllBytes(new File(filePath).toPath());
        }
        else
        {
            return null;
        }

    }


    public Set<ImageModel> prepareFiles(MultipartFile[] files, Set<ImageModel> images, String directory) throws IOException {


        for (MultipartFile file: files)
        {
            String filePath = directory + generateUniqueName();

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

    public String generateUniqueName() {
        // Generate a random string (UUID is a common way to generate random strings)
        String randomString = UUID.randomUUID().toString().substring(0, 10);

        // Get the current date in a specific format ( YYYYMMDD)
        String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

        // Combine the random string and current date
        return randomString + currentDate;
    }

    public void removeFile(String directoryPath, String fileName) {
        Path filePath = Paths.get(directoryPath, fileName);

        try
        {
            Files.delete(filePath);
            System.out.println("File " + fileName + " removed successfully from " + directoryPath);
        }
        catch (IOException e)
        {
            System.err.println("Error removing file " + fileName + " from " + directoryPath + ": " + e.getMessage());
        }
    }

    ImageModel findImageByName(String imageName){
        return imageModelRepository.findByName(imageName);
    }
}
