package com.example.demo.Bean;

import com.example.demo.repo.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MemberConfig {
    CommandLineRunner commandLineRunner(
            MemberRepository memberRepository
    ){
        return args -> {
            Member phillip = new Member(
                    "phillip",
                    "123"
            );
            Member fk = new Member(
                    "Username",
                    "123"
            );
            memberRepository.saveAll(
                    List.of(phillip,fk)
            );

        };
    }
}
