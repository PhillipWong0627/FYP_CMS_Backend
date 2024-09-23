package com.example.demo.service;

import com.example.demo.Bean.Event;
import com.example.demo.Bean.Member;
import com.example.demo.repo.MemberRepository;
import com.example.demo.result.CodeMsg;
import com.example.demo.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public Optional<Member> getMemberById(Long id){
        return memberRepository.findById(id);
    }

    public Result<Map<String, Object>> login(String email, String password) {
        Optional<Member> memberOptional = memberRepository.findByEmail(email);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();

            // Directly compare the stored password with the entered password
            if (member.getPassword().equals(password)) {
                // Login successful, return member data
                Map<String, Object> memberData = new HashMap<>();
                memberData.put("memberID", member.getId());
                memberData.put("memberName", member.getMemberName());
                memberData.put("email", member.getEmail());
                memberData.put("points", member.getPoints());   

                return Result.success(memberData);  // Login successful
            } else {
                return Result.error(CodeMsg.PASSWORD_ERROR);
            }
        }

        return Result.error(CodeMsg.EMAIL_NOT_EXIST);
    }

    // Method to make a payment and add points
    public Result<Boolean> addPoints(Long memberId, int points){
        Optional<Member> memberOptional = memberRepository.findById(memberId);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            member.setPoints(member.getPoints() + points);  // Add points

            // Add points equivalent to the payment amount
            memberRepository.save(member);

            return Result.success(true);
        }

        return Result.error(CodeMsg.EMAIL_NOT_EXIST);

    }
    public Result<Integer> getMemberPoints(Long memberId) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);

        if (memberOptional.isPresent()) {
            return Result.success(memberOptional.get().getPoints());
        }

        return Result.error(CodeMsg.EMAIL_NOT_EXIST);
    }


    // Update an existing facility
    public Member updateMember(Long id, Member updatedMember) {
        return memberRepository.findById(id).map(member -> {
            member.setMemberName(updatedMember.getMemberName());
            member.setAddress(updatedMember.getAddress());
            member.setContact(updatedMember.getContact());
            member.setDescription(updatedMember.getDescription());
            return memberRepository.save(member);
        }).orElseThrow(() -> new RuntimeException("Member not found with id " + id));
    }
    public Result<Boolean> deleteMember(Long id) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        if(memberOptional.isPresent()) {
            Member deleteMember = memberOptional.get();
            deleteMember.setDeleted(true);
            memberRepository.save(deleteMember);

            return Result.success(true);
        }
        return Result.error(CodeMsg.SERVER_ERROR);
    }

        // Get events joined by a member
    public List<Event> getJoinedEvents(Long memberId) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if (memberOptional.isPresent()) {
            return memberOptional.get().getEvents();
        } else {
            throw new RuntimeException("Member not found");
        }
    }
    public Result<Member> updateAvatar(Long memberId, String avatarUrl) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            member.setAvatar(avatarUrl); // Update avatar URL
            memberRepository.save(member); // Save updated member to the database
            return Result.success(member);
        } else {
            return Result.error(CodeMsg.EMAIL_NOT_EXIST);
        }
    }



}


