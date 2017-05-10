package jp.co.hyron.ope.service;

import jp.co.hyron.ope.entity.User;
import jp.co.hyron.ope.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * DBを参照してユーザ情報を提供します。
 */
@Component
public class JdbcUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /*
     * (非 Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.isEmpty()) {
            throw new UsernameNotFoundException("username is empty");
        }

        User foundUser = userRepository.findByUserId(username);
        if (foundUser != null) {
            return foundUser;
        }
        throw new UsernameNotFoundException(username + " is not found");
    }

    public boolean userExists(String userid) {
        try {
            loadUserByUsername(userid);
        } catch (UsernameNotFoundException e) {
            return false;
        }
        return true;
    }

    public void createUser(User user) {
        userRepository.saveAndFlush(user);
    }
}
