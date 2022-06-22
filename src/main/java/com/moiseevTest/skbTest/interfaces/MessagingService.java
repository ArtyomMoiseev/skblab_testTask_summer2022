package com.moiseevTest.skbTest.interfaces;

import org.springframework.messaging.Message;

import java.util.concurrent.TimeoutException;

public interface MessagingService {

    /**
     * Отправка сообщения в шину.
     *
     * @param msg сообщение для отправки.
     * @return идентификатор отправленного сообщения (correlationId)
     */
    <T> Long send(Message<T> msg);

    /**
     * Встает на ожидание ответа по сообщению с messageId.
     * <p>
     * Редко, но может кинуть исключение по таймауту.
     *
     * @param messageId идентификатор сообщения, на которое ждем ответ.
     * @return Тело ответа.
     */
    <T> Message<T> receive(Long messageId) throws TimeoutException;

    /**
     * Отправляем сообщение и ждем на него ответ.
     *
     * @param request тело запроса.
     */
    <R, A> void doRequest(Message<R> request) throws TimeoutException;
}
