package com.example.demo.service;

import com.example.demo.Bean.Event;
import com.example.demo.Bean.Member;
import com.example.demo.Bean.Reward;
import com.example.demo.repo.MemberRepository;
import com.example.demo.repo.RewardRepository;
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
    private final RewardRepository rewardRepository;
    @Autowired
    public MemberService(MemberRepository memberRepository, RewardRepository rewardRepository) {
        this.memberRepository = memberRepository;
        this.rewardRepository = rewardRepository;
    }

    public List<Member> getMember(){
        return memberRepository.findAll();
    }

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

    public Result<List<Reward>> getRedeemedRewards(Long memberId) {
        Optional<Member> memberOpt = memberRepository.findById(memberId);

        if (memberOpt.isEmpty()) {
            return Result.error(CodeMsg.MEMBER_NOT_EXIST);
        }

        Member member = memberOpt.get();
        List<Reward> redeemedRewards = member.getRedeemedRewards();

        return Result.success(redeemedRewards);
    }

    public Result<Boolean> redeemReward(Long memberId, Long rewardId) {
        // Find the member by ID
        Optional<Member> memberOpt = memberRepository.findById(memberId);
        if (memberOpt.isEmpty()) {
            return Result.error(CodeMsg.MEMBER_NOT_EXIST);
        }

        Member member = memberOpt.get();

        // Find the reward by ID
        Optional<Reward> rewardOpt = rewardRepository.findById(rewardId);
        if (rewardOpt.isEmpty()) {
            return Result.error(CodeMsg.REWARD_NOT_EXIST);
        }

        Reward reward = rewardOpt.get();
        // Check if the member has already redeemed the reward
        if (member.getRedeemedRewards().contains(reward)) {
            return Result.error(CodeMsg.ALREADY_REDEEMED);
        }

        // Check if the member has enough points
        if (member.getPoints() < reward.getPoints()) {
            return Result.error(CodeMsg.NOT_ENOUGH_POINTS);
        }

        // Deduct the points from the member
        member.setPoints(member.getPoints() - reward.getPoints());

        // Add the reward to the member's redeemed rewards
        member.getRedeemedRewards().add(reward);

        // Save the member with updated points and redeemed rewards
        memberRepository.save(member);

        return Result.success(true);
    }





}


