package com.example.gymcenterapp.interfaces;

import com.example.gymcenterapp.entities.Member;

import java.util.List;

public interface IMemberService
{
    Member registerMember(Member member);

    List<Member> retrieveAllMembers();

    Member retrieveMember(String email);

    void deleteMember(Long id);

    Member updateMember(Long id, Member member);
}
