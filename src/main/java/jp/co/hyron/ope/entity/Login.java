package jp.co.hyron.ope.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import jp.co.hyron.ope.dto.LoginDto;
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
public class Login implements UserDetails {

    private static final long serialVersionUID = 8111266801755973605L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id;
    
    @Column(name = "user_id" , unique = true, nullable = false)
    protected String userId;

    @Column(name = "password")
    protected String password;
    
    @Column(name = "displayName")
    private String displayName;

    @Column(name = "authorities")
    protected String authorities;

    @Column(name = "ac_sts")
    private int acSts;

    @Column(name = "pwd_err_cnt")
    private int pwdErrCnt;

    @Column(name = "enabled")
    private boolean enabled = true;

    public Login(String userId, String password, String displayName, boolean enabled, String authorities) {
        this.userId = userId;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
        this.displayName = displayName;
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
        return this.displayName;
    }

    public void convertToLoginUser(LoginDto dto, boolean isAdmin) {
    	if(isAdmin){
    		this.enabled = dto.isEnabled();
    		this.acSts = dto.getAcSts();
    		this.pwdErrCnt =  dto.getPwdErrCnt();
    	}
    	if(dto.getPassWord() !=null && !"".equals(dto.getPassWord())){
    		PasswordEncoder encoder = new BCryptPasswordEncoder();
        	String encodePassword = encoder.encode(dto.getPassWord());
        	if(!encodePassword.equals(this.password)){
        		this.password = encodePassword;
        	}
    	}    	
    }

    public boolean isRoleUser(String role) {
        return this.authorities == role;
    }
}
