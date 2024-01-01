package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.ImageModel;
import com.example.gymcenterapp.repositories.ImageModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ImageModelService
{

    ImageModelRepository imageModelRepository;

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


    public Set<ImageModel> prepareFiles(MultipartFile[] files, Set<ImageModel> images, String directory) throws IOException {


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
