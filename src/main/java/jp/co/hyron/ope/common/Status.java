package jp.co.hyron.ope.common;

import lombok.Getter;

/**
 * @author li_x
 */
@Getter
public enum Status {

    NOTACTIVE((short) 0, "未激活"), ACTIVE((short) 1, "正常"), ACTIVEERROR((short) 2, "激活異常"), UNEMPLYED((short) 8, "離職"), LOCK((short) 9, "ロック");

    private short code;

    private String text;

    Status(short code, String text) {
        this.code = code;
        this.text = text;
    }

    public static Status fromCode(short code) {
        for (Status gender : Status.values()) {
            if (gender.getCode() == code) {
                return gender;
            }
        }
        throw new UnsupportedOperationException("The code " + code + " is not supported!");
    }
}