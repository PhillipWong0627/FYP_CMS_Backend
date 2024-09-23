package com.example.demo.service;

import com.example.demo.Bean.Member;
import com.example.demo.Bean.Reward;
import com.example.demo.result.CodeMsg;
import com.example.demo.repo.MemberRepository;
import com.example.demo.repo.RewardRepository;
import com.example.demo.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RewardService {
    private final RewardRepository rewardRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public RewardService(RewardRepository rewardRepository, MemberRepository memberRepository) {
        this.rewardRepository = rewardRepository;
        this.memberRepository = memberRepository;
    }

    // Create or Update Reward
    public Reward saveReward(Reward reward) {
        return rewardRepository.save(reward);
    }

    // Get All Rewards
    public List<Reward> getAllRewards() {
        return rewardRepository.findAll();
    }

    // Get Reward By ID
    public Optional<Reward> getRewardById(Long id) {
        return rewardRepository.findById(id);
    }

    // Delete Reward
    public void deleteReward(Long id) {
        rewardRepository.deleteById(id);
    }

    public Result<String> redeemProduct(Long rewardId, Long memberId) {
        Optional<Reward> rewardOpt = rewardRepository.findById(rewardId);
        Optional<Member> memberOpt = memberRepository.findById(memberId);

        if (rewardOpt.isEmpty()) {
            return Result.error(CodeMsg.REWARD_NOT_EXIST);
        }

        if (memberOpt.isEmpty()) {
            return Result.error(CodeMsg.MEMBER_NOT_EXIST);
        }

        Reward reward = rewardOpt.get();
        Member member = memberOpt.get();

        if (member.getPoints() < reward.getPoints()) {
            return Result.error(CodeMsg.NOT_ENOUGH_POINTS);
        }

        // Deduct points and update stock
        member.setPoints(member.getPoints() - reward.getPoints());
        reward.setAvailableStock(reward.getAvailableStock() - 1);

        if (reward.getAvailableStock() < 0) {
            return Result.error(CodeMsg.OUT_OF_STOCK);
        }

        // Save updated member and reward
        memberRepository.save(member);
        rewardRepository.save(reward);

        return Result.success("Redemption successful! You've redeemed " + reward.getProductName());
    }


}
