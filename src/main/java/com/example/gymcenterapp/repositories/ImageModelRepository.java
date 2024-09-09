package com.example.gymcenterapp.repositories;

import com.example.gymcenterapp.entities.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageModelRepository extends JpaRepository<ImageModel, Long>
{
    @Query("SELECT img FROM ImageModel img WHERE img.imageName = :imageName")
    ImageModel findByName(String imageName);
}
