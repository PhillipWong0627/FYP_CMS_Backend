package com.example.demo.Bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "rewards")

public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private int points; // Points required to redeem the reward

    @Column(nullable = false)
    private String productImageUrl; // Image URL of the reward

    @Column(nullable = false)
    private String productDescription;

    private int availableStock; // Optional: track available quantity of rewards

    private Boolean isActive; // To determine if the reward is available for redemption

    // Many-to-Many relationship with Member
    @ManyToMany(mappedBy = "redeemedRewards")
    @JsonIgnore
    private List<Member> members = new ArrayList<>();

    // Constructors
    public Reward() {}

    public Reward(String productName, int points, String productImageUrl, String productDescription, int availableStock, Boolean isActive, List<Member> members) {
        this.productName = productName;
        this.points = points;
        this.productImageUrl = productImageUrl;
        this.productDescription = productDescription;
        this.availableStock = availableStock;
        this.isActive = isActive;
        this.members = members;
    }
}
