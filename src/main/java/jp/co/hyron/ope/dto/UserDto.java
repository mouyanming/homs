package jp.co.hyron.ope.dto;

import java.sql.Timestamp;
import java.util.Date;

import javax.validation.constraints.Pattern;

import lombok.Data;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class UserDto {

    /** ユーザ名 */
    @NotEmpty
    private String userId;

    /** パスワード */
    @Length(min = 6, max = 15)
    @Pattern(regexp = "^[A-Za-z0-9`~!@#$%^&*()_+-={}|\\\\:\\\";'<>?,./]+$")
    private String passWord;

    /** パスワード（再入力） */
    @Length(min = 6, max = 15)
    @Pattern(regexp = "^[A-Za-z0-9`~!@#$%^&*()_+-={}|\\\\:\\\";'<>?,./]+$")
    private String passWordTwice;

    /** 権限 */
    private String authorities;

    @NotEmpty
    private String displayName;

    private int acSts;

    private Timestamp crtTm;

    private Date epDt;

    private String jsgKb;

    private Date lfDt;

    private String spUsrId;

    private Date usrBth;

    private String usrMb;

    private String usrMl;

    private String usrNm;

    private int usrSex;

    private String usrTtl;

}
