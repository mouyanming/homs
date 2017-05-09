package jp.co.hyron.ope.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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

    @Column(name = "display_name")
    protected String displayName;

    @Column(name = "password")
    protected String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected List<Authorities> authorities;

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
    private boolean enabled;

    public User(String userId, String password, String displayName, boolean enabled, List<Authorities> authorities) {
        this.userId = userId;
        this.password = password;
        this.enabled = enabled;
        this.displayName = displayName;
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
        for (Authorities auth : this.authorities) {
            return_list.add(new SimpleGrantedAuthority(auth.getId().getAuthority()));
        }
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
}
