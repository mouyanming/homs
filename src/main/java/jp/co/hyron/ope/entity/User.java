package jp.co.hyron.ope.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import jp.co.hyron.ope.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 本システム用 のユーザ情報
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    /**
     *
     */
    private static final long serialVersionUID = 8111266801755973605L;

    @Id
    @Column(name = "user_id")
    protected String userId;

    @Column(name = "password")
    protected String password;

    @Column(name = "authorities")
    protected String authorities;

    @Column(name = "ac_sts")
    private int acSts;

    @Column(name = "crt_tm", nullable = false)
    private Timestamp crtTm;

    @Temporal(TemporalType.DATE)
    @Column(name = "ep_dt")
    private Date epDt;

    @Column(name = "jsg_kb", length = 1)
    private String jsgKb;

    @Temporal(TemporalType.DATE)
    @Column(name = "lf_dt")
    private Date lfDt;

    @Column(name = "pwd_err_cnt")
    private int pwdErrCnt;

    @Column(name = "sp_usr_id", length = 20)
    private String spUsrId;

    @Column(name = "upd_tm", nullable = false)
    private Timestamp updTm;

    @Temporal(TemporalType.DATE)
    @Column(name = "usr_bth")
    private Date usrBth;

    @Column(name = "usr_mb", length = 15)
    private String usrMb;

    @Column(name = "usr_ml", length = 30)
    private String usrMl;

    @Column(name = "usr_nm", length = 30)
    private String usrNm;

    @Column(name = "usr_sex")
    private int usrSex;

    @Column(name = "usr_ttl", length = 20)
    private String usrTtl;

    @Column(name = "enabled")
    private boolean enabled = true;

    public User(String userId, String password, String displayName, boolean enabled, String authorities) {
        this.userId = userId;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    /*
     * (非 Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*
     * (非 Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
     */
    @Override
    public boolean isAccountNonLocked() {
        return acSts != 1;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> return_list = new ArrayList<GrantedAuthority>();
        return_list.add(new SimpleGrantedAuthority(this.authorities));
        return return_list;
    }

    /*
     * (非 Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    public void convertToUser(UserDto dto, boolean isAdmin) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        // 変更あるかどうかチェック
        if (this.userId == null || "".equals(this.userId)) {
            this.userId = dto.getUserId();
        }
        if (dto.getPassWord() != null && !"".equals(dto.getPassWord())) {
            if (this.password != encoder.encode(dto.getPassWord())) {
                this.password = encoder.encode(dto.getPassWord());
            }
        }

        // 自分更新不可の項目
        if (isAdmin) {
            if (this.usrNm != dto.getUsrNm()) {
                this.usrNm = dto.getUsrNm();
            }
            if (this.usrSex != dto.getUsrSex()) {
                this.usrSex = dto.getUsrSex();
            }
            if (this.usrMl != dto.getUsrMl()) {
                this.usrMl = dto.getUsrMl();
            }
            if (this.spUsrId != dto.getSpUsrId()) {
                this.spUsrId = dto.getSpUsrId();
            }
            if (this.jsgKb != dto.getJsgKb()) {
                this.jsgKb = dto.getJsgKb();
            }
            if (this.epDt != dto.getEpDt()) {
                this.epDt = dto.getEpDt();
            }
            if (this.lfDt != dto.getLfDt()) {
                this.lfDt = dto.getLfDt();
            }
            if (this.acSts != dto.getAcSts()) {
                this.acSts = dto.getAcSts();
            }
            if (this.pwdErrCnt != dto.getPwdErrCnt()) {
                this.pwdErrCnt = dto.getPwdErrCnt();
            }
            if (this.usrTtl != dto.getUsrTtl()) {
                this.usrTtl = dto.getUsrTtl();
            }
            if (this.authorities != dto.getAuthorities()) {
                this.authorities = dto.getAuthorities();
            }
            if (this.enabled != dto.isEnabled()) {
                this.enabled = dto.isEnabled();
            }
        }
        if (this.usrBth != dto.getUsrBth()) {
            this.usrBth = dto.getUsrBth();
        }
        if (this.usrMb != dto.getUsrMb()) {
            this.usrMb = dto.getUsrMb();
        }
        this.updTm = new Timestamp(System.currentTimeMillis());

    }

    public boolean isRoleUser(String role) {
        return this.authorities == role;
    }
}
