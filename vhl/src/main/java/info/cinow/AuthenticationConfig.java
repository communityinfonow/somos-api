package info.cinow;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import info.cinow.controller.LocationSuggestController;

/**
 * AuthenticationConfig
 */
@Configuration
@EnableWebSecurity
public class AuthenticationConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    Environment env;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.logout().logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))).and()
                .authorizeRequests().antMatchers("/login").permitAll().antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/admin/users").hasRole("SUPER_USER").and().httpBasic().and().csrf().disable().cors();
        // .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("OPTIONS");
        configuration.addAllowedMethod("DELETE");
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.addAllowedOrigin(env.getProperty("app.origin"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Allows semicolon for use in the {@link LocationSuggestController }
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.httpFirewall(allowUrlSemicolonhHttpFirewall());
    }

    @Bean
    public HttpFirewall allowUrlSemicolonhHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowSemicolon(true);
        return firewall;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN")
        // .and().withUser("user").password(passwordEncoder().encode("userPass")).roles("USER");

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select email_address,password, true as enabled from user_table where lower (email_address)=?")
                .authoritiesByUsernameQuery("SELECT U.email_address, R.name FROM USER_TABLE U, USERS_ROLES UR, ROLE R "
                        + "WHERE U.id = UR.user_id AND R.id = UR.role_id and U.email_address = ?")
                .passwordEncoder(passwordEncoder());

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}