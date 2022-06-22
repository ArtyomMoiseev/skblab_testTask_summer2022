package com.moiseevTest.skbTest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 DTO web контроллера пользователя с валидацией.
 */
@Data
@AllArgsConstructor
public class RegistrationFromWebDto {

    @NotBlank(message = "username can't be blank")
    private String username;

    @Email(message = "email is not valid")
    private String email;

    @Size(min = 6, max = 120, message = "password len must be between 6 and 120 symbols")
    private String password;

    @NotBlank(message = "Name can't be blank")
    @Size(max = 40, message = "Name must be less than 40 characters")
    private String name;

    @NotBlank(message = "lastName can't be blank")
    @Size(max = 40, message = "LastName must be less than 40 characters")
    private String lastName;

    @NotBlank(message = "middleName can't be blank")
    @Size(max = 40, message = "MiddleName must be less than 40 characters")
    private String middleName;
}
