package jp.co.hyron.ope.secure.listener;

import jp.co.hyron.ope.common.CommonConst;
import jp.co.hyron.ope.common.Status;
import jp.co.hyron.ope.entity.UserMst;
import jp.co.hyron.ope.repository.UserMstRepository;
import jp.co.hyron.ope.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class LoginFailureEventListenter implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private UserMstRepository userMstRepository;

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        if (event.getException().getClass().equals(UsernameNotFoundException.class)) {
            // 存在しないユーザ名の場合は無視
            return;
        }

        String userId = event.getAuthentication().getName();

        UserMst user = userMstRepository.findUsrByUsrId(userId);
        int failedLoginAttempts = 0;
        if (user != null) {
            user.setPwdErrCnt((short) (user.getPwdErrCnt() + 1));
            failedLoginAttempts = user.getPwdErrCnt();
            if (failedLoginAttempts >= CommonConst.LOGIN_ATTEMPTS_THRESHOLD) {
                // lock
                user.setAcSts(Status.LOCK);
            }
            userMstRepository.saveAndFlush(user);
        }

    }
}