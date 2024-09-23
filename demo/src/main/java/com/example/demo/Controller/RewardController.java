package com.example.demo.Controller;

import com.example.demo.Bean.Reward;
import com.example.demo.result.CodeMsg;
import com.example.demo.result.Result;
import com.example.demo.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/admin")
public class RewardController {
    private final RewardService rewardService;

    @Autowired
    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }


    // Create a Reward
    @PostMapping("/reward/create")
    public Result<Reward> createReward(@RequestBody Reward reward) {
        try {
            Reward savedReward = rewardService.saveReward(reward);
            return Result.success(savedReward);
        } catch (Exception e) {
            return Result.error(CodeMsg.SERVER_ERROR.fillArgs("Failed to create reward"));
        }
    }

    // Get all Rewards
    @GetMapping("/reward/list")
    public Result<List<Reward>> getAllRewards() {
        List<Reward> rewards = rewardService.getAllRewards();
        return Result.success(rewards);
    }

    // Get Reward by ID
    @GetMapping("/reward/getById/{id}")
    public Result<Optional<Reward>> getRewardById(@PathVariable Long id) {
        Optional<Reward> reward = rewardService.getRewardById(id);
        if (reward.isPresent()) {
            return Result.success(reward);
        } else {
            return Result.error(CodeMsg.SERVER_ERROR.fillArgs("Reward not found"));
        }
    }

    // Update a Reward
    @PutMapping("/reward/updateById/{id}")
    public Result<Reward> updateReward(@PathVariable Long id, @RequestBody Reward rewardDetails) {
        try {
            Optional<Reward> existingReward = rewardService.getRewardById(id);
            if (existingReward.isPresent()) {
                Reward reward = existingReward.get();
                reward.setProductName(rewardDetails.getProductName());
                reward.setPoints(rewardDetails.getPoints());
                reward.setProductImageUrl(rewardDetails.getProductImageUrl());
                reward.setProductDescription(rewardDetails.getProductDescription());
                reward.setAvailableStock(rewardDetails.getAvailableStock());
                reward.setIsActive(rewardDetails.getIsActive());

                Reward updatedReward = rewardService.saveReward(reward);
                return Result.success(updatedReward);
            } else {
                return Result.error(CodeMsg.SERVER_ERROR.fillArgs("Reward not found"));
            }
        } catch (Exception e) {
            return Result.error(CodeMsg.SERVER_ERROR.fillArgs("Failed to update reward"));
        }
    }

    // Delete a Reward
    @DeleteMapping("/reward/deleteById/{id}")
    public Result<Boolean> deleteReward(@PathVariable Long id) {
        try {
            rewardService.deleteReward(id);
            return Result.success(true);
        } catch (Exception e) {
            return Result.error(CodeMsg.SERVER_ERROR.fillArgs("Failed to delete reward"));
        }
    }



}
