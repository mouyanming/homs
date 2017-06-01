package jp.co.hyron.ope.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

/**
 * 共通メッセージ処理クラス
 * @author li_x
 */
@Component
public class MessageAccess {
    private MessageSourceAccessor messageSourceAccessor;

    @Autowired
    public void setMessages(MessageSource messageSource) {
        messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }

    /**
     * メッセージを取得
     * @param key メッセージキー
     * @return メッセージ
     */
    public String getMessage(String key) {
        String msg = "";
        msg = messageSourceAccessor.getMessage(key);
        return msg;
    }

    /**
     * メッセージを取得（パラメータ付く）
     * @param key メッセージキー
     * @param str メッセージパラメータ
     * @return メッセージ
     */
    public String getMessage(String key, String[] str) {
        String msg = "";
        msg = messageSourceAccessor.getMessage(key, str);
        return msg;
    }

}
