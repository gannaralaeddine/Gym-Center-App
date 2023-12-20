package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.Member;
import com.example.gymcenterapp.services.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/member")
@AllArgsConstructor

public class MemberController
{
    MemberService memberService;


    @GetMapping("/retrieve-all-members")
    @ResponseBody
    public List<Member> getAllMembers() { return memberService.retrieveAllMembers(); }


    @GetMapping("/retrieve-member/{member-id}")
    @ResponseBody
    public Member retrieveMember(@PathVariable("member-id") Long memberId) { return memberService.retrieveMember(memberId); }


    @PostMapping(value = "/add-member")
    @ResponseBody
    public Member addMember(@RequestBody Member member) { return memberService.addMember(member); }

    @PutMapping(value = "/update-member/{member-id}")
    @ResponseBody
    public Member updateMember(@PathVariable("member-id") Long memberId,@RequestBody Member member) { return memberService.updateMember(memberId,member); }

    @DeleteMapping(value = "/delete-member/{member-id}")
    public void deleteMember(@PathVariable("member-id") Long memberId) { memberService.deleteMember(memberId); }
}
