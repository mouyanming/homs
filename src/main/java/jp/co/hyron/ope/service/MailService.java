package jp.co.hyron.ope.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private MailSender mailSender;

    @Autowired
    private MessageSource messageSource;

    @Async
    // 別スレッドで実行される
    public void sendMail(String mailAddr) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mailAddr);
        msg.setSubject(messageSource.getMessage("mail.subject", null, Locale.JAPAN));
        msg.setText(String.format(messageSource.getMessage("mail.content", null, Locale.JAPAN), "url"));
        mailSender.send(msg);
    }
}
