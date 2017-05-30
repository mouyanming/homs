package jp.co.hyron.ope.common;

import lombok.Getter;

@Getter
public enum Department {
    D1(100, "第一本部"), D2(200, "第二本部"), D3(300, "第三本部"), D4(400, "第四本部"), D5(500, "第五本部"), D6(600, "第六本部"), D7(700, "第七本部"), D8(800, "第八本部"), D9(900, "第九本部"), D10(1000, "管理部"), D0(0, "未入力");
    private int code;

    private String text;

    private Department(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static Department fromCode(int code) {
        for (Department e : Department.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        throw new UnsupportedOperationException("The code " + code + " is not supported!");
    }

}
