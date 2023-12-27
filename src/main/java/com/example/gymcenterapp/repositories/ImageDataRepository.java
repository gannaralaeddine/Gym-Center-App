package com.example.gymcenterapp.repositories;

import com.example.gymcenterapp.entities.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ImageDataRepository extends JpaRepository<ImageData, Long>
{
    @Query("SELECT img FROM ImageData img WHERE img.imageName = :imageName")
    Optional<ImageData> findByName(String imageName);
}
