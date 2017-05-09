package jp.co.hyron.ope.dto;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

import javax.validation.constraints.Pattern;

import jp.co.hyron.ope.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    private int acSts;

    private Timestamp crtTm;

    private Date epDt;

    private String jsgKb;

    private Date lfDt;

    private String spUsrId;

    private Date usrBth;

    private String usrMb;

    private String usrMl;

    @NotEmpty
    private String usrNm;

    private int usrSex;

    private int pwdErrCnt;

    private String usrTtl;

    private boolean enbaled = true;

    public UserDto(User usr) {
        this.userId = usr.getUserId();
        Collection<? extends GrantedAuthority> list = usr.getAuthorities();
        this.authorities = list.toArray()[0].toString();
        this.acSts = usr.getAcSts();
        this.crtTm = usr.getCrtTm();
        this.epDt = usr.getEpDt();
        this.jsgKb = usr.getJsgKb();
        this.lfDt = usr.getLfDt();
        this.spUsrId = usr.getSpUsrId();
        this.usrBth = usr.getUsrBth();
        this.usrNm = usr.getUsrNm();
        this.usrSex = usr.getUsrSex();
        this.usrTtl = usr.getUsrTtl();
        this.enbaled = usr.isEnabled();
    }
}
