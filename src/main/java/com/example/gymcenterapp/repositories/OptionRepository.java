package com.example.gymcenterapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.gymcenterapp.entities.Option;

@Repository
public interface OptionRepository extends JpaRepository <Option, Long> { }
