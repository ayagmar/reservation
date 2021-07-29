package com.adservio.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;
    public void SendEmail(String To,String body,String Subject){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(To);
        message.setText(body);
        message.setSubject(Subject);

        javaMailSender.send(message);

    }
}
