package jp.co.hyron.ope.dto;

import java.util.Collection;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import jp.co.hyron.ope.entity.Login;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    /** login名 */
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

    private int pwdErrCnt;

    private boolean enabled = true;

    public LoginDto(Login loginUsr) {
        this.userId = loginUsr.getUserId();
        Collection<? extends GrantedAuthority> list = loginUsr.getAuthorities();
        this.authorities = list.toArray()[0].toString();
        this.acSts = loginUsr.getAcSts();
        this.enabled = loginUsr.isEnabled();
    }
}
