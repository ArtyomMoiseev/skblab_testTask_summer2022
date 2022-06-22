package com.moiseevTest.skbTest.service;

import com.moiseevTest.skbTest.dto.FormComplianceMessageDto;
import com.moiseevTest.skbTest.events.FormCreatedEvent;
import com.moiseevTest.skbTest.events.FormStatusUpdatedEvent;
import com.moiseevTest.skbTest.model.RegistrationRecordStatus;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeoutException;


@RunWith(SpringRunner.class)
@SpringBootTest
class EventListeningTest {



    @Autowired ApplicationEventPublisher applicationEventPublisher;
    @MockBean
    private FormComplianceMessageServiceStub formComplianceMessageServiceStub;
    @MockBean
    private MailNotifyServiceStub mailNotifyServiceStub;
    @MockBean
    private RegistrationService registrationService;
    @Autowired
    private EventListening eventListening;

    @Test
    @Disabled
    void handleNewForm() throws InterruptedException, TimeoutException {
        var formCreateEvent = new FormCreatedEvent(new FormComplianceMessageDto(null, "testUser", "test@test.com",
                "firstName","lastName", "middleName"));

        eventListening.HandleNewForm(formCreateEvent);

        Mockito.verify(formComplianceMessageServiceStub, Mockito.atLeastOnce())
                .doRequest(new GenericMessage<>(formCreateEvent.formComplianceMessageDto()));
    }

    @Test
    @Disabled
    void formStatusUpdateMail() throws InterruptedException, TimeoutException {
        var formCompliance = new FormComplianceMessageDto(null, "testUser", "test@test.com",
                "firstName","lastName", "middleName");

        eventListening.FormStatusUpdateMail(new FormStatusUpdatedEvent(formCompliance, RegistrationRecordStatus.APPROVED));

        Mockito.verify(mailNotifyServiceStub, Mockito.atLeastOnce()).sendMail("test@test.com", "Уважаемый firstName middleName, ваша форма изменила статус на APPROVED");


    }

}