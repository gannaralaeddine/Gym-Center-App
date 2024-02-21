package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.Member;
import com.example.gymcenterapp.entities.Session;
import com.example.gymcenterapp.services.MemberService;
import lombok.AllArgsConstructor;
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


    @PostMapping(value = "/register-member")
    @ResponseBody
    public Member registerMember(@RequestBody Member member) { return memberService.registerMember(member); }

    @PutMapping(value = "/update-member/{member-id}")
    @ResponseBody
    public Member updateMember(@PathVariable("member-id") Long memberId,@RequestBody Member member) { return memberService.updateMember(memberId,member); }

    @DeleteMapping(value = "/delete-member/{member-id}")
    public void deleteMember(@PathVariable("member-id") Long memberId) { memberService.deleteMember(memberId); }


    @GetMapping("/retrieve-member-sessions/{email}")
    @ResponseBody
    public Set<Session> retrieveMemberSessions(@PathVariable("email") String email) { return memberService.retrieveMemberSessions(email); }
}
