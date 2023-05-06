package com.example.laba8.service;

import com.example.laba8.model.*;
import com.example.laba8.repository.CitizenshipRepository;
import com.example.laba8.repository.CityRepository;
import com.example.laba8.repository.GenderReporitory;
import com.example.laba8.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GenderReporitory genderReporitory;
    private final CityRepository cityRepository;
    private final CitizenshipRepository citizenshipRepository;

    public User createUser(User user){
        return userRepository.save(user);
    }

    public ResponseEntity<?> deleteUser(Long id){
        if (!userRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public ResponseEntity<?> updateUser(UserDTO newUser, Long id){
        User user = userRepository.findById(id).map(user1 -> {
            user1.setName(newUser.getName());
            user1.setLastname(newUser.getLastname());
            user1.setPatronymic(newUser.getPatronymic());
            user1.setBirthDate(newUser.getBirthDate());

            Gender gender = genderReporitory.getById(newUser.getGender());
            user1.setGender(gender);

            user1.setPassport_seria(newUser.getPassport_seria());
            user1.setPassport_number(newUser.getPassport_number());

            City city = cityRepository.getById(newUser.getCity());
            user1.setCity(city);

            user1.setNumber_home(newUser.getNumber_home());
            user1.setPhoneNumber(newUser.getPhoneNumber());

            Citizenship citizenship = citizenshipRepository.getById(newUser.getCitizenship());
            user1.setCitizenship(citizenship);

            return userRepository.save(user1);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
