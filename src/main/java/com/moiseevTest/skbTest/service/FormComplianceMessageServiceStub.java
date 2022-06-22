package com.moiseevTest.skbTest.service;

import com.moiseevTest.skbTest.dto.FormComplianceMessageDto;
import com.moiseevTest.skbTest.events.FormStatusUpdatedEvent;
import com.moiseevTest.skbTest.interfaces.MessagingService;
import com.moiseevTest.skbTest.model.RegistrationRecordStatus;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 Заглушка сервиса одобрения формы.
 */
@AllArgsConstructor
@Service
@Slf4j
public class FormComplianceMessageServiceStub implements MessagingService {

    private ApplicationEventPublisher applicationEventPublisher;

    @SneakyThrows
    private static void sleep() {
        Thread.sleep(TimeUnit.MINUTES.toMillis(1));
    }

    private static boolean shouldSleep() {
        return new Random().nextInt(10) == 1;
    }

    private static boolean shouldThrowTimeout() {
        return new Random().nextInt(10) == 1;
    }

    @Override
    public <T> Long send(Message<T> msg) {
        log.info(String.format("Sent JMS message: %s", msg.getPayload()));
        return new Random().nextLong();
    }

    @Override
    public Message receive(Long messageId) throws TimeoutException {
        if (shouldThrowTimeout()) {
            sleep();

            throw new TimeoutException("Timeout!");
        }

        if (shouldSleep()) {
            sleep();
        }

        if (new Random().nextInt(10) == 1) {
            return new GenericMessage<>(RegistrationRecordStatus.REJECTED);
        }
        return new GenericMessage<>(RegistrationRecordStatus.APPROVED);
    }

    @Override
    public <R, A> void doRequest(Message<R> request) throws TimeoutException {
        final long messageId = send(request);
        var result = receive(messageId);
        applicationEventPublisher.publishEvent(new FormStatusUpdatedEvent((FormComplianceMessageDto) request.getPayload(), (RegistrationRecordStatus) result.getPayload()));
    }

}
