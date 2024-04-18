package com.example.gymcenterapp.repositories;

import com.example.gymcenterapp.entities.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long>
{
        ConfirmationToken findByConfirmationToken(String confirmationToken);
}
