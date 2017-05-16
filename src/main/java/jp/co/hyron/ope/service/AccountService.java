package jp.co.hyron.ope.service;

import java.util.ArrayList;
import java.util.List;

import jp.co.hyron.ope.common.CommonConst;
import jp.co.hyron.ope.dto.AccountDto;
import jp.co.hyron.ope.entity.UserMst;
import jp.co.hyron.ope.repository.UserMstRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private JdbcUserDetailsManager userDetailsService;

    @Autowired
    private UserMstRepository userMstRepository;

    public boolean createNewAccount(AccountDto account) {
        try {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(CommonConst.DEF_AUTHOR_ROLE_NORMAL_USER));
            User userDetails = new User(account.getEmail(), encoder.encode(account.getPassword()), authorities);
            userDetailsService.createUser(userDetails);
            UserMst usr = new UserMst();
            usr.convertToUser(account);
            usr.setAcSts((short) 1);
            userMstRepository.saveAndFlush(usr);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
