package com.moiseevTest.skbTest.service;

import com.moiseevTest.skbTest.dto.FormComplianceMessageDto;
import com.moiseevTest.skbTest.dto.RegistrationFromWebDto;
import com.moiseevTest.skbTest.events.FormCreatedEvent;
import com.moiseevTest.skbTest.model.RegistrationRecord;
import com.moiseevTest.skbTest.model.RegistrationRecordStatus;
import com.moiseevTest.skbTest.repo.RegistrationRecordRepo;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RegistrationServiceTest {

    @Autowired
    public RegistrationService registrationService;

    @MockBean
    public RegistrationRecordRepo registrationRecordRepo;

    @Autowired
    public ApplicationEventPublisher applicationEventPublisher;

    @MockBean
    public EventListening eventListening;

    @Test
    public void registerNewUser() throws InterruptedException {
        var userFromWeb = new RegistrationFromWebDto("testUser", "test@test.com", "password",
                "firstName", "lastName", "middleName");

        var formCreateEvent = new FormCreatedEvent(new FormComplianceMessageDto(null, "testUser", "test@test.com",
                "firstName","lastName", "middleName"));

        Assert.assertTrue(registrationService.registerNewUser(userFromWeb));
        Mockito.verify(eventListening, Mockito.times(1)).HandleNewForm(formCreateEvent);
        // Нет теста обращения к репозиторию, надо тестовую конфигурацию, чтоб можно было мокнуть passwordEncoder.
    }

    @Test
    public void userExist() {
        var userFromWeb = new RegistrationFromWebDto("testUser", "test@test.com", "password",
                "firstName", "lastName", "middleName");
        registrationService.registerNewUser(userFromWeb);

        Mockito.verify(registrationRecordRepo, Mockito.times(1)).existsByEmail("test@test.com");
        Mockito.verify(registrationRecordRepo, Mockito.times(1)).existsByUsername("testUser");
    }

    @Test
    @Ignore
    public void updateRecordStatus() {
        var userFromWeb = new RegistrationFromWebDto("testUser", "test@test.com", "password",
                "firstName", "lastName", "middleName");

        var baseRecord = new RegistrationRecord("testUser", "test@test.com",
                "password", "firstName", "lastName",
                "middleName", RegistrationRecordStatus.NOT_SEND);

        Mockito.when(registrationRecordRepo.findById(Mockito.any(long.class))).thenReturn(Optional.of(baseRecord));
        registrationService.updateRecordStatus(0L, RegistrationRecordStatus.APPROVED);

        var newBaseRecord = new RegistrationRecord("testUser", "test@test.com",
                "password", "firstName", "lastName",
                "middleName", RegistrationRecordStatus.APPROVED);

        Mockito.verify(registrationRecordRepo, Mockito.times(1)).save(newBaseRecord);
    }
}