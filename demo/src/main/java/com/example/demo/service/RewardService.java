package com.example.demo.service;

import com.example.demo.Bean.Reward;
import com.example.demo.repo.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RewardService {
    private final RewardRepository rewardRepository;

    @Autowired
    public RewardService(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
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

}
