package jp.co.hyron.ope.secure;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import jp.co.hyron.ope.entity.Authorities;
import jp.co.hyron.ope.entity.User;
import jp.co.hyron.ope.entity.type.AuthoritiesId;
import jp.co.hyron.ope.service.JdbcUserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcUserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.authorizeRequests().antMatchers("/css/**").permitAll()
        // .anyRequest().fullyAuthenticated()
        // .and().formLogin()
        // .loginPage("/login").failureUrl("/login?error").permitAll().and().logout().permitAll();
        http.authorizeRequests().antMatchers("/admin/css/**").permitAll().antMatchers("/admin/js/**").permitAll().antMatchers("/admin/bower_components/**").permitAll().anyRequest().authenticated()
                .and().formLogin().loginPage("/login").usernameParameter("userid").passwordParameter("password").defaultSuccessUrl("/index.html").failureUrl("/login?error").permitAll().and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout").deleteCookies("JSESSIONID").invalidateHttpSession(true).permitAll().and().rememberMe()
                .tokenRepository(jdbcTokenRepository()).tokenValiditySeconds(604800);// remember for a week. ( 1 * 60 * 60 * 24 * 7 ) sec
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
        // 管理者がないの場合、管理者を追加する
        if (!userDetailsService.userExists("admin")) {
            List<Authorities> authorities = new ArrayList<Authorities>();
            Authorities a = new Authorities();
            a.setId(new AuthoritiesId("admin", "ROLE_ADMIN"));
            authorities.add(a);
            User user = new User("admin", encoder.encode("admin"), "管理者", true, authorities);
            userDetailsService.createUser(user);
        }
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
