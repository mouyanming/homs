package jp.co.hyron.ope.service;

import jp.co.hyron.ope.common.MessageAccess;
import lombok.RequiredArgsConstructor;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final MailSender mailSender;

    private final MessageAccess messageAccess;

    @Async
    // 別スレッドで実行される
    public void sendMail(String mailAddr, String url) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mailAddr);
        msg.setSubject(messageAccess.getMessage("mail.subject"));
        StringBuffer sb = new StringBuffer();
        sb.append("ご利用ありがとうございます。").append(System.getProperty("line.separator"));
        sb.append("HOMS登録メールのご案内です。 ").append(System.getProperty("line.separator"));
        sb.append("以下のURLから登録を完了してください。 ").append(System.getProperty("line.separator"));
        sb.append(url).append(System.getProperty("line.separator"));
        sb.append("※このリンクの有効期限は24時間です。期限を過ぎてしまった場合には初めからやり直してください 。").append(System.getProperty("line.separator"));
        sb.append("※メール送信前と同じブラウザで上記URLを開いてください。 ※心当たりの無い方は、このメールを削除してください。").append(System.getProperty("line.separator"));
        sb.append("※このメールアドレスは送信専用です。 ").append(System.getProperty("line.separator"));
        sb.append("※本メールの転送を禁じます。").append(System.getProperty("line.separator"));
        msg.setText(sb.toString());
        mailSender.send(msg);

    }

    @Async
    // 別スレッドで実行される
    public void sendPassword(String mailAddr, String newPassword) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mailAddr);
        msg.setSubject(messageAccess.getMessage("mail.subject.setnewpassword"));
        StringBuffer sb = new StringBuffer();
        sb.append("パスワード更新しました。").append(System.getProperty("line.separator"));
        sb.append("新しいパスワードは下記となります ").append(System.getProperty("line.separator"));
        sb.append(newPassword).append(System.getProperty("line.separator"));
        msg.setText(sb.toString());
        mailSender.send(msg);

    }
}
