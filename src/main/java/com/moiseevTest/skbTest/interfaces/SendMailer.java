package com.moiseevTest.skbTest.interfaces;


import java.util.concurrent.TimeoutException;

public interface SendMailer {
    void sendMail (String toAddress, String messageBody) throws TimeoutException;
}