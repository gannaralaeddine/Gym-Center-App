package com.example.gymcenterapp.interfaces;

import com.example.gymcenterapp.entities.Member;

import java.util.List;

public interface IMemberService
{
    Member addMember(Member member);

    List<Member> retrieveAllMembers();

    Member retrieveMember(Long id);

    void deleteMember(Long id);

    Member updateMember(Long id, Member member);
}
