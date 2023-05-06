package com.example.laba8.repository;

import com.example.laba8.model.City;
import com.example.laba8.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {

    City getById(Long city);
}
