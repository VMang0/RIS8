package com.example.laba8.repository;

import com.example.laba8.model.Gender;
import com.example.laba8.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderReporitory extends JpaRepository<Gender, Long> {

    Gender getById(Long gender);
}
