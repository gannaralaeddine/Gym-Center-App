package com.example.gymcenterapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.gymcenterapp.entities.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> { }
