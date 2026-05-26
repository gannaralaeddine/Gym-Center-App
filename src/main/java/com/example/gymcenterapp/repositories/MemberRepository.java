package com.example.gymcenterapp.repositories;

import com.example.gymcenterapp.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(" select user from Member user where user.userEmail = :email ")
    Member findByEmail(String email);

    @Query("SELECT COUNT(*) FROM Member user WHERE user.userEmail = :email ")
    int numberOfUsersByEmail(String email);

    List<Member> findByUserIdNotIn(List<Long> ids);

}
