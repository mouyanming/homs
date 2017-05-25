package jp.co.hyron.ope.secure;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcUserDetailsManager userDetailsService() {
        JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager();
        userDetailsService.setDataSource(dataSource);
        return userDetailsService;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/vendors/**", "/fonts/**", "/themes/**", "/admin/css/**", "/admin/js/**", "/admin/bower_components/**");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        auth.userDetailsService(userDetailsService()).passwordEncoder(encoder);
        auth.jdbcAuthentication().dataSource(dataSource);
        // 管理者がないの場合、管理者を追加する
        if (!userDetailsService().userExists("admin")) {
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            User userDetails = new User("admin", encoder.encode("admin"), authorities);
            userDetailsService().createUser(userDetails);
        }

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/account/**").permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login").defaultSuccessUrl("/index.html").failureUrl("/login?error")
                .permitAll().and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout").deleteCookies("JSESSIONID").invalidateHttpSession(true)
                .permitAll().and().rememberMe().tokenRepository(jdbcTokenRepository()).tokenValiditySeconds(604800);// remember for a week. ( 1 * 60 * 60 * 24 * 7 ) sec
        http.authorizeRequests().antMatchers("/console/**").permitAll();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    /**
     * Remember Me 認証に利用するトークンのリポジトリ
     * @return
     */
    @Bean
    public PersistentTokenRepository jdbcTokenRepository() {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }

}
