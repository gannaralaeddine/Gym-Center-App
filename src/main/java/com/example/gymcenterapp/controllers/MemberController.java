package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.*;
import com.example.gymcenterapp.services.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/member")
@AllArgsConstructor

public class MemberController
{
    MemberService memberService;

    @GetMapping("/retrieve-all-members")
    @ResponseBody
    public List<Member> getAllMembers() { return memberService.retrieveAllMembers(); }


    @GetMapping("/retrieve-member/{email}")
    @ResponseBody
    public Member retrieveMember(@PathVariable("email") String email) { return memberService.retrieveMember(email); }


    @GetMapping("/retrieve-member-by-id/{id}")
    @ResponseBody
    public Member retrieveMemberById(@PathVariable("id") Long id) { return memberService.retrieveMemberById(id); }


    @PostMapping(value = "/register-member")
    @ResponseBody
    public ResponseEntity<String> registerMember(@RequestBody Member member) { return memberService.registerMember(member); }

    @PutMapping(value = "/update-member/{member-id}")
    @ResponseBody
    public Member updateMember(@PathVariable("member-id") Long memberId,@RequestBody Member member) 
    { 
        return memberService.updateMember(memberId,member); 
    }

    @DeleteMapping(value = "/delete-member/{member-id}")
    public void deleteMember(@PathVariable("member-id") Long memberId) { memberService.deleteMember(memberId); }


    @GetMapping("/retrieve-member-sessions/{email}")
    @ResponseBody
    public Set<Session> retrieveMemberSessions(@PathVariable("email") String email) { return memberService.retrieveMemberSessions(email); }

    @PutMapping(value = "/private-coach-booking/{memberEmail}/{coachEmail}")
    @ResponseBody
    public ResponseEntity<String> coachBooking(@PathVariable String memberEmail, @PathVariable String coachEmail) { return memberService.privateCoachBooking(memberEmail, coachEmail); }

    @GetMapping("/retrieve-private-coaches/{memberEmail}")
    @ResponseBody
    public Set<Coach> retrievePrivateCoaches(@PathVariable("memberEmail") String memberEmail) { return memberService.retrievePrivateCoaches(memberEmail); }


    @GetMapping("/is-my-private-coach/{memberEmail}/{coachEmail}")
    public boolean isMyPrivateCoach(@PathVariable String memberEmail, @PathVariable String coachEmail)
    {
        return memberService.isMyPrivateCoach(memberEmail, coachEmail);
    }

    @GetMapping("/send-invitation-to-coach/{memberEmail}/{coachEmail}")
    @ResponseBody
    public String sendInvitationToCoach(@PathVariable String memberEmail, @PathVariable String coachEmail)
    {
        return memberService.sendInvitationToCoach(memberEmail, coachEmail);
    }

    @GetMapping("/getMemberNotifications/{memberEmail}")
    @ResponseBody
    public Set<NotificationMemberCoach> getMemberNotifications(@PathVariable String memberEmail)
    {
        return memberService.getMemberNotifications(memberEmail);
    }


    @PutMapping(value = "/coach-booking/{memberEmail}/{privateSessionId}")
    @ResponseBody
    public ResponseEntity<String> coachBooking(@PathVariable String memberEmail, @PathVariable Long privateSessionId) { return memberService.coachBooking(memberEmail, privateSessionId); }



    @GetMapping("/get-member-private-sessions/{memberEmail}")
    @ResponseBody
    public Set<PrivateSession> getMemberPrivateSessions(@PathVariable String memberEmail)
    {
        return memberService.getMemberPrivateSessions(memberEmail);
    }


    @GetMapping("/get-member-subscriptions/{memberEmail}")
    @ResponseBody
    public Set<Subscription> getMemberSubscriptions(@PathVariable String memberEmail)
    {
        return memberService.getMemberSubscriptions(memberEmail);
    }

    @PutMapping(value = "/update-member-private-sessions-number/{memberEmail}/{newPrivateSessionsNumber}")
    @ResponseBody
    public void updateMemberPrivateSessionsNumber(@PathVariable String memberEmail, @PathVariable Integer newPrivateSessionsNumber) 
    { 
        memberService.updateMemberPrivateSessionsNumber(memberEmail, newPrivateSessionsNumber); 
    }
}
