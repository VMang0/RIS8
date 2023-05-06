package com.example.laba8.controller;

import com.example.laba8.model.*;
import com.example.laba8.repository.CitizenshipRepository;
import com.example.laba8.repository.CityRepository;
import com.example.laba8.repository.GenderReporitory;
import com.example.laba8.service.UserService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private GenderReporitory genderReporitory;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CitizenshipRepository citizenshipRepository;
    @PostMapping("/user/add")
    public ResponseEntity<String> newUser(@RequestBody User user) throws IOException {
        Pattern phonePattern = Pattern.compile("^\\+375\\d{9}$");
        Matcher phoneMatcher = phonePattern.matcher(user.getPhoneNumber());
        if (!phoneMatcher.matches()) {
            String xmlResponse = "<message>Номер телефона должен быть введён в следующем формате: +375XXXXXXXXX</message>";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/xml");
            return new ResponseEntity<>(xmlResponse, headers, HttpStatus.BAD_REQUEST);
        }
        Pattern homePattern = Pattern.compile("^\\d{8}$");
        Matcher homeMatcher = homePattern.matcher(user.getNumber_home());
        if (!homeMatcher.matches()) {
            String xmlResponse = "<message>Номер телефона должен быть введён в следующем формате: XXXXXXXX</message>";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/xml");
            return new ResponseEntity<>(xmlResponse, headers, HttpStatus.BAD_REQUEST);
        }

        Pattern passportNumberPattern = Pattern.compile("^\\d{7}$");
        Matcher passportNumberMatcher = passportNumberPattern.matcher(user.getPassport_number());
        if (!passportNumberMatcher.matches()) {
            String xmlResponse = "<message>Номер паспорта должен быть введён в следующем формате: XXXXXXX без MC</message>";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/xml");
            return new ResponseEntity<>(xmlResponse, headers, HttpStatus.BAD_REQUEST);
        }
        User createUser = userService.createUser(user);
        XmlMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(createUser);
        return new ResponseEntity<>(xml, HttpStatus.CREATED);
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        String xmlResponse = "<message>User deleted successfully</message>";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/xml");
        return new ResponseEntity<>(xmlResponse, headers, HttpStatus.OK);
    }

    @GetMapping(value = "/user/list", produces = "application/xml")
    @ResponseBody
    List<User> getAllRooms(){return userService.findAll();}

    @PutMapping("/user/edit/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO newUser, @PathVariable Long id) {
        Pattern phonePattern = Pattern.compile("^\\+375\\d{9}$");
        Matcher phoneMatcher = phonePattern.matcher(newUser.getPhoneNumber());
        if (!phoneMatcher.matches()) {
            String xmlResponse = "<message>Номер телефона должен быть введён в следующем формате: +375XXXXXXXXX</message>";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/xml");
            return new ResponseEntity<>(xmlResponse, headers, HttpStatus.BAD_REQUEST);
        }
        Pattern homePattern = Pattern.compile("^\\d{8}$");
        Matcher homeMatcher = homePattern.matcher(newUser.getNumber_home());
        if (!homeMatcher.matches()) {
            String xmlResponse = "<message>Номер телефона должен быть введён в следующем формате: XXXXXXXX</message>";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/xml");
            return new ResponseEntity<>(xmlResponse, headers, HttpStatus.BAD_REQUEST);
        }

        Pattern passportNumberPattern = Pattern.compile("^\\d{7}$");
        Matcher passportNumberMatcher = passportNumberPattern.matcher(newUser.getPassport_number());
        if (!passportNumberMatcher.matches()) {
            String xmlResponse = "<message>Номер паспорта должен быть введён в следующем формате: XXXXXXX без MC</message>";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/xml");
            return new ResponseEntity<>(xmlResponse, headers, HttpStatus.BAD_REQUEST);
        }
        userService.updateUser(newUser, id);
        String xmlResponse = "<message>User updated successfully</message>";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/xml");
        return new ResponseEntity<>(xmlResponse, headers, HttpStatus.OK);
    }

    @GetMapping(value = "/city/list", produces = "application/xml")
    @ResponseBody
    List<City> getAllCity(){return cityRepository.findAll();}
    @GetMapping(value = "/gender/list",produces = "application/xml")
    List<Gender> getAllGender(){return genderReporitory.findAll();}
    @GetMapping(value = "/citizenship/list" , produces = "application/xml")
    List<Citizenship> getAllCitizenship(){return citizenshipRepository.findAll();}
}
