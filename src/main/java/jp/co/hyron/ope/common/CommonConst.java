package jp.co.hyron.ope.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonConst {

    // 権限区分 0:一般ユーザー 1:システム管理者 2:事務処理者 4:契約事務 8:出張情報登録者 16:上位管理者

    // システム管理権限
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    // 上位管理者権限
    public static final String ROLE_MANAGER = "ROLE_MANAGER";

    // 一般権限
    public static final String ROLE_NORMAL_USER = "ROLE_NORMAL_USER";

    // 事務処理権限
    public static final String ROLE_JIMU = "ROLE_JIMU";

    // 契約事務処理権限
    public static final String ROLE_KEIYAKU = "ROLE_KEIYAKU";

    // 出張情報登録者
    public static final String ROLE_SYUTYO = "ROLE_SYUTYO";

    // 所属区分一覧
    public static final List<Values> affilications = Arrays.asList(Affiliation.values());

    public static final Map<String, String> authorites = new HashMap<String, String>() {
        /**
         *
         */
        private static final long serialVersionUID = -1350886761560363850L;
        {
            put(ROLE_ADMIN, "システム管理");
            put(ROLE_MANAGER, "上位管理者");
            put(ROLE_NORMAL_USER, "一般ユーザー");
            put(ROLE_JIMU, "事務処理者");
            put(ROLE_KEIYAKU, "契約事務処理");
            put(ROLE_SYUTYO, "出張情報登録者");
        }

    };

}
