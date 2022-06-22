package com.moiseevTest.skbTest.service;

import com.moiseevTest.skbTest.dto.FormComplianceMessageDto;
import com.moiseevTest.skbTest.dto.RegistrationFromWebDto;
import com.moiseevTest.skbTest.events.FormCreatedEvent;
import com.moiseevTest.skbTest.model.RegistrationRecord;
import com.moiseevTest.skbTest.model.RegistrationRecordStatus;
import com.moiseevTest.skbTest.repo.RegistrationRecordRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 Сервис регистрации и управления статусами пользователей.
 */
@AllArgsConstructor
@Service
@Slf4j
public class RegistrationService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private RegistrationRecordRepo registrationRecordRepo;
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     Регистрируем нового пользователя. Хэшируем его пароль. Порождаем событие регистрации новой формы.
     */
    public boolean registerNewUser(RegistrationFromWebDto registrationForm) {

        if (userExist(registrationForm.getEmail(), registrationForm.getUsername()))
            return false;

        var hashedPassword = passwordEncoder.encode(registrationForm.getPassword());

        var record = new RegistrationRecord(registrationForm.getUsername(), registrationForm.getEmail(),
                hashedPassword, registrationForm.getName(), registrationForm.getLastName(),
                registrationForm.getMiddleName(), RegistrationRecordStatus.NOT_SEND);
        registrationRecordRepo.save(record);

        applicationEventPublisher.publishEvent(new FormCreatedEvent(new FormComplianceMessageDto(record.getId(), record.getUsername(), record.getEmail(),
                record.getName(), record.getLastName(), record.getMiddleName())));

        return true;
    }

    /**
     Проверка наличия пользователя в БД.
     */
    public boolean userExist(String email, String username) {
        return registrationRecordRepo.existsByEmail(email) ||
                registrationRecordRepo.existsByUsername(username);
    }

    /**
     Обновление статуса одобрения формы.
     */
    public void updateRecordStatus(Long formId, RegistrationRecordStatus newStatus) {
        if (registrationRecordRepo.findById(formId).isEmpty())
            return;
        var record = registrationRecordRepo.findById(formId).get();
        record.setStatus(newStatus);
        registrationRecordRepo.save(record);
        log.info(String.format("RegistrationService: record with id:%s updated, to status: %s ", formId, newStatus));
    }

}
