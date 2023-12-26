package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.Member;
import com.example.gymcenterapp.interfaces.IMemberService;
import com.example.gymcenterapp.repositories.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor

public class MemberService implements IMemberService
{ 
    MemberRepository memberRepository;

    @Override
    public Member addMember(Member member) { return memberRepository.save(member); }

    @Override
    public List<Member> retrieveAllMembers() { return memberRepository.findAll(); }

    @Override
    public Member retrieveMember(Long id) { return memberRepository.findById(id).orElse(null); }

    @Override
    public void deleteMember(Long id) { memberRepository.deleteById(id);}

    @Override
    public Member updateMember(Long id, Member member)
    {
        Member existingMember = memberRepository.findById(id).orElse(null);

        if (existingMember != null)
        {
            existingMember.setUserEmail(member.getUserEmail());
            existingMember.setUserBirthDate(member.getUserBirthDate());
            existingMember.setUserPhoneNumber(member.getUserPhoneNumber());
            existingMember.setUserCity(member.getUserCity());
            existingMember.setUserState(member.getUserCountry());
            existingMember.setUserCountry(member.getUserCountry());
            existingMember.setUserGender(member.getUserGender());
            existingMember.setUserHeight(member.getUserHeight());
            existingMember.setUserWeight(member.getUserWeight());
            existingMember.setUserPicture(member.getUserPicture());
            existingMember.setUserZipCode(member.getUserZipCode());
            existingMember.setUserPassword(member.getUserPassword());
            existingMember.setRoles(member.getRoles());
            existingMember.setMemberSessions(member.getMemberSessions());
            existingMember.setMemberSubscriptions(member.getMemberSubscriptions());
            return memberRepository.save(existingMember);
        }

        return null;
    }
}
