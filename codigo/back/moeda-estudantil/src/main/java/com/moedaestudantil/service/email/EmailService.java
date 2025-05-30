package com.moedaestudantil.service.email;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void sendCoinReceivedEmail(String to, Integer amount, String message) {
        System.out.printf("Email to %s: You received %d coins - %s%n", to, amount, message);
    }
}
