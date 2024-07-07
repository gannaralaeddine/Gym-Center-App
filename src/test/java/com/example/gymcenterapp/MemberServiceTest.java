package com.example.gymcenterapp;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import com.example.gymcenterapp.entities.Member;
import com.example.gymcenterapp.entities.PrivateSession;
import com.example.gymcenterapp.entities.ConfirmationToken;
import com.example.gymcenterapp.services.CoachService;
import com.example.gymcenterapp.services.MemberService;
import com.example.gymcenterapp.services.PrivateSessionService;
import com.example.gymcenterapp.repositories.ConfirmationTokenRepository;
import com.example.gymcenterapp.repositories.MemberRepository;

@SpringBootTest
public class MemberServiceTest 
{
    @Autowired
    private MemberService memberService;

    @Autowired
    private CoachService coachService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PrivateSessionService privateSessionService;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Test
    public void registerMember() 
    {
        Member member = new Member();
        member.setUserEmail("hello@gmail.com");
        member.setUserFirstName("ghassen");
        member.setUserLastName("awadi");
        member.setUserDescription("description de l'utilisateur");
        member.setUserGender("Homme");
        member.setUserPassword("000000");
        member.setUserIsSubscribed(false);
        member.setUserIsEnabled(false);
        ResponseEntity<String> confirmationTokenResponseEntity = memberService.registerMember(member);
        assertNotNull(confirmationTokenResponseEntity);
        confirmationTokenRepository.deleteById(Long.valueOf(confirmationTokenResponseEntity.getBody()));
    }

    @Test
    public void retrieveAllMembers() { assertNotNull(memberService.retrieveAllMembers()); }

    @Test
    public void retrieveMember() 
    {
        Member member = new Member();
        member.setUserEmail("hello@gmail.com");
        member.setUserFirstName("ghassen");
        member.setUserLastName("awadi");
        member.setUserDescription("description de l'utilisateur");
        member.setUserGender("Homme");
        member.setUserPassword("000000");
        member.setUserIsSubscribed(false);
        member.setUserIsEnabled(false);
        ResponseEntity<String> confirmationTokenResponseEntity = memberService.registerMember(member);
        ConfirmationToken confirmationToken = confirmationTokenRepository.findById(Long.valueOf(confirmationTokenResponseEntity.getBody())).orElse(null);
        assertNotNull(memberService.retrieveMemberById(confirmationToken.getUser().getUserId()));
        confirmationTokenRepository.deleteById(Long.valueOf(confirmationTokenResponseEntity.getBody()));
    }

    @Test
    public void retrieveMemberSessions()
    {
        List<Member> members = memberService.retrieveAllMembers();
        assertNotNull(memberService.retrieveMemberSessions(members.get(0).getUserEmail()));
    }

    @Test
    public void updateMember()
    {
        Member member = new Member();
        member.setUserEmail("hello@gmail.com");
        member.setUserFirstName("ghassen");
        member.setUserLastName("awadi");
        member.setUserDescription("description de l'utilisateur");
        member.setUserGender("Homme");
        member.setUserPassword("000000");
        member.setUserIsSubscribed(false);
        member.setUserIsEnabled(false);
        ResponseEntity<String> confirmationTokenResponseEntity = memberService.registerMember(member);
        member.setUserEmail("awadifiras@gmail.com");
        member.setUserFirstName("firas");
        member.setUserLastName("awadi");
        member = memberRepository.save(member);
        assertNotNull(memberService.retrieveMemberById(member.getUserId()));
        confirmationTokenRepository.deleteById(Long.valueOf(confirmationTokenResponseEntity.getBody()));
    }

    @Test
    public void retrievePrivateCoaches()
    {
        List<Member> members = memberService.retrieveAllMembers();
        assertNotNull(memberService.retrievePrivateCoaches(members.get(0).getUserEmail()));
    }

    @Test
    public void privateCoachBooking()
    {
        List<Member> members = memberService.retrieveAllMembers();
        Set<PrivateSession> privateSessions = privateSessionService.retrieveAvailablePrivateSessions();
        ResponseEntity<String> response = memberService.coachBooking(members.get(0).getUserEmail(), privateSessions.iterator().next().getPrivateSessionId());
        assertNotEquals(404,response.getStatusCode().value());
        assertNotEquals(404,privateSessionService.cancelPrivateSession(members.get(0).getUserEmail(), privateSessions.iterator().next().getPrivateSessionId()).getStatusCode().value());
    }

    @Test
    public void updateMemberPrivateSessionsNumber()
    {
        Member member = memberService.retrieveAllMembers().get(0);
        Integer oldPrivateSessionsNumber = member.getPrivateSessionsNumber();
        memberService.updateMemberPrivateSessionsNumber(member.getUserEmail(), 10);
        member = memberService.retrieveMemberById(member.getUserId());
        assertNotEquals(oldPrivateSessionsNumber, member.getPrivateSessionsNumber());
        member.setPrivateSessionsNumber(oldPrivateSessionsNumber);
        memberService.updateMember(member.getUserId(), member);
    }

    @Test
    public void replaceMemberPrivateSessionsNumber()
    {
        Member member = memberService.retrieveAllMembers().get(0);
        Integer oldPrivateSessionsNumber = member.getPrivateSessionsNumber();
        memberService.replaceOldMemberPrivateSessionsNumber(member.getUserId(), 10);
        member = memberService.retrieveMemberById(member.getUserId());
        assertNotEquals(oldPrivateSessionsNumber, member.getPrivateSessionsNumber());
        member.setPrivateSessionsNumber(oldPrivateSessionsNumber);
        memberService.updateMember(member.getUserId(), member);
    }

    @Test
    public void retrieveMemberSubscriptions()
    {
        Member member = memberService.retrieveAllMembers().get(0);
        assertNotNull(memberService.getMemberSubscriptions(member.getUserEmail()));
    }

    @Test
    public void retrieveMemberPrivateSessions()
    {
        Member member = memberService.retrieveAllMembers().get(0);
        assertNotNull(memberService.getMemberPrivateSessions(member.getUserEmail()));
    }
}
