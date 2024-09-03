package com.example.demo.Bean;

import jakarta.persistence.*;

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

    public Member() {
    }

    public Member(Long id, String memberName, String password) {
        this.id = id;
        this.memberName = memberName;
        this.password = password;
    }

    public Member(String memberName, String password) {
        this.memberName = memberName;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", memberName='" + memberName + '\'' +
                ", password='" + password + '\'' +
                '}';
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
}
