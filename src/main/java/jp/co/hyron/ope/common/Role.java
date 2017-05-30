package jp.co.hyron.ope.common;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_ADMIN("システム管理者"), ROLE_MANAGER("上位管理者"), ROLE_NORMAL_USER("一般ユーザ"), ROLE_JIMU("事務処理"), ROLE_KEIYAKU("契約事務処理"), ROLE_SYUTYO("出張情報登録者");

    private String text;

    private Role(String text) {
        this.text = text;
    }
}
