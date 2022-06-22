package com.moiseevTest.skbTest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity(name = "userTable")
public class RegistrationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private @Getter Long id;
    @NotBlank
    private @Getter
    @Setter String username;
    @NotBlank
    private @Getter
    @Setter String email;
    @NotBlank
    private @Getter
    @Setter String password;
    @NotBlank
    private @Getter
    @Setter String name;
    @NotBlank
    private @Getter
    @Setter String lastName;
    @NotBlank
    private @Getter
    @Setter String middleName;
    private @Getter
    @Setter RegistrationRecordStatus status;

    public RegistrationRecord(String username, String email, String password, String name, String lastName, String middleName, RegistrationRecordStatus status) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.middleName = middleName;
        this.status = status;
    }

    public RegistrationRecord() {

    }
}
