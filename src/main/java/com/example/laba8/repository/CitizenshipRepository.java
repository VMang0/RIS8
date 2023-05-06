package com.example.laba8.repository;

import com.example.laba8.model.Citizenship;
import com.example.laba8.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitizenshipRepository extends JpaRepository<Citizenship, Long> {

    Citizenship getById(Long citizenship);
}
