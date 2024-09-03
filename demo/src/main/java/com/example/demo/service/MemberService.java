package com.example.demo.service;

import com.example.demo.Bean.Member;
import com.example.demo.repo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getMember(){
        return memberRepository.findAll();
    }

//    public void addNewMember(Member member) {
//        Optional<Member> memberOptional =  memberRepository
//                .findByName(member.getMemberName());
//
//        if(memberOptional.isPresent()){
//            throw new IllegalStateException("Name Taken");
//        }
//        memberRepository.save(member);
//    }
    public void addNewMember(Member member) {
        // Check if the member with the same name already exists
        Optional<Member> memberOptional = memberRepository
                .findByMemberName(member.getMemberName());

        if (memberOptional.isPresent()) {
            throw new IllegalStateException("Name Taken");
        }

        // Save the new member to the database
        memberRepository.save(member);
    }

}

//Bean = CREATE TABLE

