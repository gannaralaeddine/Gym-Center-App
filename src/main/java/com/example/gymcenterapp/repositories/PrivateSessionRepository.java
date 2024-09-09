package com.example.gymcenterapp.repositories;

import com.example.gymcenterapp.entities.PrivateSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface PrivateSessionRepository extends JpaRepository<PrivateSession, Long>
{
    @Query("SELECT session FROM PrivateSession session WHERE session.privateSessionIsReserved = false ")
    Set<PrivateSession> retrieveAvailablePrivateSessions();
}
