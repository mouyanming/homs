package jp.co.hyron.ope.common;

public enum Gender {
    MALE((short) 1), FEMALE((short) 0);

    private short code;

    Gender(short code) {
        this.code = code;
    }

    public short getCode() {
        return code;
    }

    public static Gender fromCode(short code) {
        for (Gender gender : Gender.values()) {
            if (gender.getCode() == code) {
                return gender;
            }
        }
        throw new UnsupportedOperationException("The code " + code + " is not supported!");
    }
}