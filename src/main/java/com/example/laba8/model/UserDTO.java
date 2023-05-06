package com.example.laba8.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class UserDTO {


    private Long id;
    private String name;

    private String lastname;

    private String patronymic;

    private Date birthDate;
    private Long gender;
    private String passport_seria;
    private String passport_number;

    private Long city;

    private String number_home;

    private String phoneNumber;

    private Long citizenship;
}
