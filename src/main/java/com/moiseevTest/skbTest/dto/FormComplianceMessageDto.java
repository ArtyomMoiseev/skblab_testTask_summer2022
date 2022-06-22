package com.moiseevTest.skbTest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 DTO для сервиса одобрения. Без полей статуса и пароля.
 */
@Data
@AllArgsConstructor
public class FormComplianceMessageDto {

    private Long id;
    private String username;
    private String email;
    private String name;
    private String lastName;
    private String middleName;
}
