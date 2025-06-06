package com.moedaestudantil.service.email;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendCoinReceivedEmail(String to, Integer amount, String message) {
        System.out.printf("Email to %s: You received %d coins - %s%n", to, amount, message);
    }

    public void sendRedemptionEmailToStudent(String to, String rewardName, String code) {
        System.out.printf("Email to %s: You redeemed '%s'. Use code: %s%n", to, rewardName, code);
    }

    public void sendNotificationToPartner(String to, String rewardName, String studentName, String code) {
        System.out.printf("Email to partner %s: '%s' redeemed. Student: %s Confirmation code: %s%n", to, rewardName, studentName, code);
    }

}
