package com.example.demo.service;

import com.example.demo.Bean.Member;
import com.example.demo.repo.MemberRepository;
import com.example.demo.result.CodeMsg;
import com.example.demo.result.Result;
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
    public Result<Boolean> addNewMember(Member member) {

        Optional<Member> memberOptional1 = memberRepository.findByEmail(member.getEmail());

        if (memberOptional1.isPresent()) {
            return Result.error(CodeMsg.EMAIL_EXIST);
//            throw new IllegalStateException("Name Taken");
        }

        // Save the new member to the database
        memberRepository.save(member);
        // Return a success message
        return Result.success(true);

    }
    public Result<Boolean> login(String email, String password) {
        Optional<Member> memberOptional = memberRepository.findByEmail(email);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();

            // Directly compare the stored password with the entered password
            if (member.getPassword().equals(password)) {
                return Result.success(true);  // Login successful
            } else {
                return Result.error(CodeMsg.PASSWORD_ERROR);
            }
        }

        return Result.error(CodeMsg.EMAIL_NOT_EXIST);
    }


}


