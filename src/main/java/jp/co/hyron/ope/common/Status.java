package jp.co.hyron.ope.common;

/**
 * @author li_x
 */
public enum Status {

    // アカウント状態。0:未激活 1:正常 2:激活異常 8:離職 9:ロック（自分更新不可、不可視）
    NOTACTIVE((short) 0), ACTIVE((short) 1), ACTIVEERROR((short) 2), UNEMPLYED((short) 8), LOCK((short) 9);

    private short code;

    Status(short code) {
        this.code = code;
    }

    public short getCode() {
        return code;
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