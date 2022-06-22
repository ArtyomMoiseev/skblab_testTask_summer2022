package com.moiseevTest.skbTest.service;

import com.moiseevTest.skbTest.events.FormCreatedEvent;
import com.moiseevTest.skbTest.events.FormStatusUpdatedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 Обрабатываем события нашего приложения.
 */
@Service
@Slf4j
@AllArgsConstructor
public class EventListening {

    private RegistrationService registrationService;
    private FormComplianceMessageServiceStub formComplianceMessageService;
    private MailNotifyServiceStub mailNotifyService;

    /**
     Асинхронно обрабатываем событие создания новой формы, и делаем три попытки её отправки в сервис одобрения.
     На каждой попытке увеличиваем время задержки.
     */
    @Async
    @EventListener
    public void HandleNewForm(FormCreatedEvent formCreatedEvent) throws InterruptedException {
        var message = new GenericMessage<>(formCreatedEvent.formComplianceMessageDto());
        var retryTime = TimeUnit.MILLISECONDS.toMillis(500);

        for (var retryCount = 1; retryCount < 3; retryCount++) {
            Thread.sleep(retryTime);
            try {
                formComplianceMessageService.doRequest(message);
                break;
            } catch (TimeoutException e) {
                log.info("JMS send timeout");
                retryTime *= retryCount;
            }
        }
    }

    /**
     Асинхронно обрабатываем событие изменения статуса формы, и делаем три попытки её отправки по почте.
     На каждой попытке увеличиваем время задержки.
     */
    @Async
    @EventListener
    public void FormStatusUpdateMail(FormStatusUpdatedEvent formStatusUpdatedEvent) throws InterruptedException {
        var form = formStatusUpdatedEvent.formComplianceMessageDto();
        var message = String.format("Уважаемый %s %s, ваша форма изменила статус на %s",form.getName(), form.getMiddleName(), formStatusUpdatedEvent.registrationRecordStatus());
        var retryTime = TimeUnit.MILLISECONDS.toMillis(200);

        for (var retryCount = 1; retryCount < 3; retryCount++) {

            try {
                mailNotifyService.sendMail(form.getEmail(), message);
                break;
            } catch (TimeoutException e) {
                log.info("Mail send timeout");
                Thread.sleep(retryTime);
                retryTime *= retryCount;
            }
        }
    }

    /**
     Асинхронно обновляем статус регистрации формы в БД.
     */
    @Async
    @EventListener
    public void FormStatusUpdateRegistration(FormStatusUpdatedEvent formStatusUpdatedEvent) {
        registrationService.updateRecordStatus(formStatusUpdatedEvent.formComplianceMessageDto().getId(),
                formStatusUpdatedEvent.registrationRecordStatus());
    }

}
