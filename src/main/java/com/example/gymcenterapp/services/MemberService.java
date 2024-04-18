package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.Member;
import com.example.gymcenterapp.entities.Role;
import com.example.gymcenterapp.entities.Session;
import com.example.gymcenterapp.interfaces.IMemberService;
import com.example.gymcenterapp.repositories.MemberRepository;
import com.example.gymcenterapp.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class MemberService implements IMemberService
{ 
    private MemberRepository memberRepository;
    private RoleRepository roleRepository;
    private EmailServiceImpl emailService;

    @Override
    public Member registerMember(Member member) {

        Set<Role> roles = new HashSet<>();

        member.setUserPassword(new BCryptPasswordEncoder().encode(member.getUserPassword()));

        member.getRoles().forEach(role -> {

            if (role.getRoleId() != null)
            {
                Role newRole = roleRepository.findById(role.getRoleId()).orElse(null);

                assert newRole != null;
                newRole.getUsers().add(member);

                roles.add(newRole);
            }
            else
            {
                roles.add(role);
            }
        });
        member.setRoles(roles);
        emailService.sendConfirmationEmail(member);
        return memberRepository.save(member);
    }

    @Override
    public List<Member> retrieveAllMembers() { return memberRepository.findAll(); }

    @Override
    public Member retrieveMember(String email) { return memberRepository.findByEmail(email); }

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

    public Set<Session> retrieveMemberSessions(String email)
    {
        Member member = memberRepository.findByEmail(email);
        return member.getMemberSessions();
    }
}
