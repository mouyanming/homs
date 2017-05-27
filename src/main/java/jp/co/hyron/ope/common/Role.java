package jp.co.hyron.ope.common;

public enum Role {
	
    ROLE_ADMIN ,
    // 上位管理者権限
    ROLE_MANAGER,

    // 一般権限
    ROLE_NORMAL_USER ,

    // 事務処理権限
    ROLE_JIMU,

    // 契約事務処理権限
    ROLE_KEIYAKU,
    // 出張情報登録者
    ROLE_SYUTYO ;
}
