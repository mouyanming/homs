package jp.co.hyron.ope.common;

import java.util.Arrays;
import java.util.List;

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

    // アカウントロックをかける連続認証失敗回数の閾値
    public static final int loginAttemptsThreshold = 5;

    // 所属区分一覧
    public static final List<Affiliation> affilications = Arrays.asList(Affiliation.values());

    public static final List<Role> authorites = Arrays.asList(Role.values());

    public static final List<Status> statusList = Arrays.asList(Status.values());

    public static final List<Position> positions = Arrays.asList(Position.values());

    public static final List<Department> departments = Arrays.asList(Department.values());

}
