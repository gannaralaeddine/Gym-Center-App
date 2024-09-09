package com.example.gymcenterapp.repositories;

import com.example.gymcenterapp.entities.Category;
import com.example.gymcenterapp.entities.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT img FROM Category img WHERE img.catImage = :imageName")
    Optional<ImageModel> findByName(String imageName);
}
