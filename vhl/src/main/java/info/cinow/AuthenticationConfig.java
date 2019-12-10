package info.cinow;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * AuthenticationConfig
 */
@Configuration
@EnableWebSecurity
public class AuthenticationConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // httpSecurity.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authentication) throws Exception {
        authentication.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select email_address,password,enabled from user_table where email_address=?")
                .authoritiesByUsernameQuery("SELECT U.email_address, R.name FROM USER_TABLE U, USERS_ROLES UR, ROLE R "
                        + "WHERE U.id = UR.user_id AND R.id = UR.role_id and U.email_address = ?")
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}