package jp.co.hyron.ope.secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import ch.qos.logback.core.net.SyslogOutputStream;
import jp.co.hyron.ope.common.Role;
import jp.co.hyron.ope.dto.AccountDto;
import jp.co.hyron.ope.service.UserService;



@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PersistentTokenRepository jdbcTokenRepository;
    
    @Autowired
    private UserService userService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/vendors/**", "/fonts/**", "/themes/**", "/admin/css/**", "/admin/js/**", "/admin/bower_components/**");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        //auth.jdbcAuthentication().dataSource(dataSource);
        // 管理者がないの場合、管理者を追加する
        if (!userService.getUserByEmail("admin").isPresent()) {  
        	System.out.println("add admin");
            AccountDto dto = new AccountDto();
            dto.setEmail("admin");
            dto.setPassword("admin");
            dto.setRole(Role.ROLE_ADMIN);
            userService.create(dto);
        }

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/account/reg/**").permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login").defaultSuccessUrl("/index.html")
                .failureUrl("/login?error").usernameParameter("email").permitAll().and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true).permitAll().and().rememberMe().tokenRepository(jdbcTokenRepository).tokenValiditySeconds(604800);// remember for a week. ( 1 * 60 * 60 * 24 * 7 ) sec
        http.authorizeRequests().antMatchers("/console/**").permitAll();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

}
