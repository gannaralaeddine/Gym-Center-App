package com.example.gymcenterapp.repositories;

import com.example.gymcenterapp.entities.Activity;
import com.example.gymcenterapp.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> { }
