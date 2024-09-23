package com.example.demo.Bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table
public class Member {
    @Id
    @SequenceGenerator(
            name = "member_sequence",
            sequenceName = "member_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String memberName;
    private String password;
    private String email;
    private String address;
    private String contact;
    private String description;
    private String avatar = "https://res.cloudinary.com/dnzypglvm/image/upload/v1727100429/ixzmrge9c9s45c4okzrf.jpg";
    private boolean isDeleted;
    private int points = 0;


    // Many-to-Many relationship with Event
    @ManyToMany(mappedBy = "members")
    @JsonIgnore
    private List<Event> events;

    public Member() {
    }

    public Member(Long id, String memberName, String password, String email, String address, String contact, String description, boolean isDeleted) {
        this.id = id;
        this.memberName = memberName;
        this.password = password;
        this.email = email;
        this.address = address;
        this.contact = contact;
        this.description = description;
        this.isDeleted = isDeleted;
    }

    public Member(String memberName, String password, String email, String address, String contact, String description, boolean isDeleted, int points, List<Event> events,String avatar) {
        this.memberName = memberName;
        this.password = password;
        this.email = email;
        this.address = address;
        this.contact = contact;
        this.description = description;
        this.isDeleted = isDeleted;
        this.points = points;
        this.events = events;
        this.avatar = avatar;

    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", memberName='" + memberName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", description='" + description + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addPoints(int points){
        this.points += points;
    }
}
