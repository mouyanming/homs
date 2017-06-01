package jp.co.hyron.ope.entity;

import jp.co.hyron.ope.common.CommonConst;
import jp.co.hyron.ope.common.Role;
import jp.co.hyron.ope.common.Status;

import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

    /**
	 *
	 */
    private static final long serialVersionUID = 856518177222816909L;

    private User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return user.getId();
    }

    public Role getRole() {
        return user.getRole();
    }

    @Override
    public boolean isAccountNonLocked() {
        if ("admin".equals(user.getEmail())) {
            return true;
        }
        boolean isNonLocked = true;
        UserMst usr = user.getUserMst();
        if (usr != null) {
            isNonLocked = usr.getPwdErrCnt() < CommonConst.LOGIN_ATTEMPTS_THRESHOLD && !usr.getAcSts().equals(Status.LOCK);
        }
        return isNonLocked;
    }

    @Override
    public boolean isEnabled() {
        if ("admin".equals(user.getEmail())) {
            return true;
        }
        boolean isEnabled = false;
        UserMst usr = user.getUserMst();
        if (usr != null) {
            isEnabled = usr.getAcSts().equals(Status.ACTIVE);
        }
        return isEnabled;
    }

}