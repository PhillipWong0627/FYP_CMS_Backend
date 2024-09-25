package com.example.demo.Bean;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    public Admin() {
    }

    public Admin(String password, String username) {
        this.password = password;
        this.username = username;
    }
}
