package com.example.gymcenterapp.repositories;

import com.example.gymcenterapp.entities.CoachMemberId;
import com.example.gymcenterapp.entities.NotificationMemberCoach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationMemberCoachRepository extends JpaRepository<NotificationMemberCoach, CoachMemberId> {


}
