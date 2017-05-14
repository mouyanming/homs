package jp.co.hyron.ope.secure.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import jp.co.hyron.ope.entity.Login;
import jp.co.hyron.ope.repository.LoginRepository;

@Component
public class LoginFailureEventListenter implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    // アカウントロックをかける連続認証失敗回数の閾値
    private int loginAttemptsThreshold = 5;

    @Autowired
    private LoginRepository loginRepository;

    public void setLoginAttemptsThreshold(int threshold) {
        this.loginAttemptsThreshold = threshold;
    }

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        if (event.getException().getClass().equals(UsernameNotFoundException.class)) {
            // 存在しないユーザ名の場合は無視
            return;
        }

        String userId = event.getAuthentication().getName();

        Login user = loginRepository.findByUserId(userId);
        if (user != null) {
            user.setPwdErrCnt(user.getPwdErrCnt() + 1);
            int failedLoginAttempts = user.getPwdErrCnt();
            if (failedLoginAttempts == loginAttemptsThreshold) {
                user.setAcSts(1);
            }
            loginRepository.saveAndFlush(user);
        }
    }
}