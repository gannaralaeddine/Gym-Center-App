package com.example.gymcenterapp.interfaces;

import com.example.gymcenterapp.entities.Member;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface IMemberService
{
    ResponseEntity<String> registerMember(Member member);

    List<Member> retrieveAllMembers();

    Member retrieveMember(String email);

    void deleteMember(Long id);

    Member updateMember(Long id, Member member);

    void updateMemberPrivateSessionsNumber(String email, Integer newNumberumberOfSessions);
}
