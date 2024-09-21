package com.example.demo.repo;

import com.example.demo.Bean.Event;
import com.example.demo.Bean.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Anything that access your database
@Repository
public interface EventRepository
        extends JpaRepository<Event,Long> {

}

