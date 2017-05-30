package jp.co.hyron.ope.secure.listener;

import jp.co.hyron.ope.common.CommonConst;
import jp.co.hyron.ope.entity.UserMst;
import jp.co.hyron.ope.repository.UserMstRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private UserMstRepository userMstRepository;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String email = event.getAuthentication().getName();
        UserMst userMst = userMstRepository.findUsrByUsrId(email);
        if (userMst != null) {
            if (userMst.getPwdErrCnt() < CommonConst.loginAttemptsThreshold) {
                userMst.setPwdErrCnt((short) 0);
                userMstRepository.saveAndFlush(userMst);
            }
        }
    }
}