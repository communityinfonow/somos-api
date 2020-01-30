package info.cinow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * AuthenticationConfigTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(AuthenticationConfigTest.class)
public class AuthenticationConfigTest {

    @Autowired
    AuthenticationManagerBuilder builder;

    private UserDetails details;

    private PasswordEncoder encoder;

    @Before
    public void setup() {
        this.encoder = new BCryptPasswordEncoder();
        this.details = builder.getDefaultUserDetailsService().loadUserByUsername("joemoran231@gmail.com");
    }

    @Test
    public void usernameIsReading() {
        assertNotNull(details);
    }

    @Test
    public void usernameIsAccurate() {
        assertEquals(details.getUsername(), "joemoran231@gmail.com");
    }

    @Test
    public void user_passwordIsCorrect() {
        assertTrue(encoder.matches("test", details.getPassword()));
    }

    @Test
    public void user_hasAuthorities() {
        assertFalse(details.getAuthorities().isEmpty());
    }

}