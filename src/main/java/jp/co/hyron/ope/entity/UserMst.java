package jp.co.hyron.ope.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 本システム用 のユーザ情報
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "userMst")
public class UserMst implements Serializable {

    private static final long serialVersionUID = -7254273334291876188L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "usrId", unique = true, nullable = false)
    protected String usrId;

    @Column(name = "crt_tm", nullable = false)
    private Timestamp crtTm;

    // 入社日付（"JP"の場合のみ）（自分更新不可）
    @Temporal(TemporalType.DATE)
    @Column(name = "ep_dt")
    private Date epDt;

    // 所属区分："JP"：日本海隆、"SH"：上海　"HZ"華　"BJ"北京 "JS"江蘇　KS　 "WZ":外注（自分更新不可）
    @Column(name = "jsg_kb", length = 4)
    private String jsgKb;

    // 離職日付（"JP"の場合のみ）（自分更新不可、不可視）
    @Temporal(TemporalType.DATE)
    @Column(name = "lf_dt")
    private Date lfDt;

    // 上位管理者（のusr_id）（自分更新不可、不可視）
    @Column(name = "sp_usr_id")
    private String spUsrId;

    @Column(name = "upd_tm", nullable = false)
    private Timestamp updTm;

    @Temporal(TemporalType.DATE)
    @Column(name = "usr_bth")
    private Date usrBth;

    @Column(name = "usr_mb", length = 15)
    private String usrMb;

    @Column(name = "usr_nm", length = 30)
    private String usrNm;

    // 部署番号　100　第一本部
    @Column(name = "usr_dept", length = 3)
    private String usrDept;

    // 性別　0:女性 1:男性（値がある状態の場合、自分更新不可）
    @Column(name = "usr_sex")
    private short usrSex;

    // 役職（自分更新不可、不可視）
    @Column(name = "usr_ttl", length = 20)
    private String usrTtl;

    // 激活コード
    @Column(name = "vd_cd", unique = true, length = 64)
    private String vdCd;

    // アカウント状態。0:未激活 1:正常 2:激活異常 8:離職 9:ロック（自分更新不可、不可視）
    @Column(name = "ac_sts")
    private short acSts;

    // パスワード入力エラー回数。5回以上ロック　（自分更新不可、不可視）
    @Column(name = "pwd_err_cnt")
    private Short pwdErrCnt;

    @Column(name = "image")
    private String image;

}
