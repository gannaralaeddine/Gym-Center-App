package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.*;
import com.example.gymcenterapp.interfaces.IMemberService;
import com.example.gymcenterapp.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private CoachRepository coachRepository;
    private NotificationMemberCoachRepository notificationRepository;
    private PrivateSessionRepository privateSessionRepository;

    @Override
    public ResponseEntity<String> registerMember(Member member) {

        if (memberRepository.numberOfUsersByEmail(member.getUserEmail()) == 0)
        {
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
            memberRepository.save(member);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.FOUND).body("User already exist! please try with another email !");
    }

    @Override
    public List<Member> retrieveAllMembers() { return memberRepository.findAll(); }

    @Override
    public Member retrieveMember(String email) { return memberRepository.findByEmail(email); }

    public Member retrieveMemberById(Long id) { return memberRepository.findById(id).orElse(null); }

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
            existingMember.setPrivateSessionsNumber(member.getPrivateSessionsNumber());
            return memberRepository.save(existingMember);
        }

        return null;
    }

    public Set<Session> retrieveMemberSessions(String email)
    {
        Member member = memberRepository.findByEmail(email);
        return member.getMemberSessions();
    }

    public ResponseEntity<String> privateCoachBooking(String memberEmail, String coachEmail)
    {
        Member member = memberRepository.findByEmail(memberEmail);
        Coach coach = coachRepository.findByEmail(coachEmail);

        if (member != null && coach != null)
        {
            Set<Member> coachMembers = coach.getPrivateMembers();
            Set<Coach> memberCoaches = member.getPrivateCoaches();

            if (coachMembers.contains(member))
            {
                return new ResponseEntity<>(HttpStatus.FOUND);
            }

            coachMembers.add(member);
            memberCoaches.add(coach);

            memberRepository.save(member);
            coachRepository.save(coach);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public boolean isMyPrivateCoach(String memberEmail, String coachEmail)
    {
        Member member = memberRepository.findByEmail(memberEmail);
        Coach coach = coachRepository.findByEmail(coachEmail);

        if (member != null && coach != null)
        {
            return coach.getPrivateMembers().contains(member);
        }
        return false;
    }

    public Set<Coach> retrievePrivateCoaches(String memberEmail)
    {
        Member member = memberRepository.findByEmail(memberEmail);
        return member.getPrivateCoaches();
    }

    public String sendInvitationToCoach(String memberEmail, String coachEmail)
    {
        Member member = memberRepository.findByEmail(memberEmail);
        Coach coach = coachRepository.findByEmail(coachEmail);

        if (member != null && coach != null)
        {
            Set<NotificationMemberCoach> memberNotifications = member.getNotificationMemberCoaches();
            Set<NotificationMemberCoach> coachNotification = coach.getNotificationMemberCoaches();

            NotificationMemberCoach notification = new NotificationMemberCoach();

            notification.setMember(member);
            notification.setCoach(coach);
            notification.setNotificationTitle("Private coach invitation");
            notification.setNotificationContent("bla bla bla bla bla bla bla !");

            memberNotifications.add(notification);
            coachNotification.add(notification);
            member.setNotificationMemberCoaches(memberNotifications);
            coach.setNotificationMemberCoaches(coachNotification);
            notificationRepository.save(notification);
            memberRepository.save(member);
            coachRepository.save(coach);

            return "invitation send successfully !";
        }
        return "member or coach is null !";
    }


    public Set<NotificationMemberCoach> getMemberNotifications(String memberEmail)
    {
        Member member = memberRepository.findByEmail(memberEmail);

        return member.getNotificationMemberCoaches();
    }

    public ResponseEntity<String> coachBooking(String memberEmail, Long privateSessionId)
    {
        PrivateSession privateSession = privateSessionRepository.findById(privateSessionId).orElse(null);
        Member member = memberRepository.findByEmail(memberEmail);

        if (privateSession != null && member != null)
        {
            privateSession.setPrivateSessionMember(member);
            privateSession.setPrivateSessionIsReserved(true);
            privateSessionRepository.save(privateSession);

            if (member.getPrivateSessionsNumber() > 0)
            {
                member.setPrivateSessionsNumber(member.getPrivateSessionsNumber() - 1);
                memberRepository.save(member);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public Set<PrivateSession> getMemberPrivateSessions(String memberEmail)
    {
        Member member = memberRepository.findByEmail(memberEmail);
        if (member != null)
        {
            return member.getMemberPrivateSessions();
        }
        return null;
    }

    public Set<Subscription> getMemberSubscriptions(String memberEmail)
    {
        Member member = memberRepository.findByEmail(memberEmail);
        if (member != null)
        {
            return member.getMemberSubscriptions();
        }
        return null;
    }

    @Override
    public void updateMemberPrivateSessionsNumber(String email, Integer newNumberumberOfSessions) 
    {
        Member member = memberRepository.findByEmail(email);
        
        if (member != null)
        {
            member.setPrivateSessionsNumber(newNumberumberOfSessions + member.getPrivateSessionsNumber());
            memberRepository.save(member);
        }
    }

    public void replaceOldMemberPrivateSessionsNumber(Long id, Integer newNumberumberOfSessions)
    {
        Member member = memberRepository.findById(id).orElse(null);

        if (member != null)
        {
            member.setPrivateSessionsNumber(newNumberumberOfSessions);
            memberRepository.save(member);
        }
    }
}

