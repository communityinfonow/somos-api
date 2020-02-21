package info.cinow.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import info.cinow.AuthenticationConfig;
import info.cinow.dto.mapper.CensusTractMapper;
import info.cinow.model.CensusTract;
import info.cinow.repository.CensusTractDao;
import info.cinow.service.CensusTractService;

/**
 * CensusTractControllerTest
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CensusTractController.class)
@Import({ AuthenticationConfig.class })
public class CensusTractControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CensusTractService service;

    @MockBean
    private CensusTractMapper tractMapper;

    @MockBean
    private CensusTractDao censusTractDao;

    // @MockBean
    // AuthenticationManagerBuilder builder;

    @MockBean
    DataSource dataSource;

    @MockBean

    // @MockBean
    // UserDetailsService userDetailsService;

    // @MockBean
    // PasswordEncoder passwordEncoder;

    // @MockBean
    // public HttpFirewall allowUrlSemicolonhHttpFirewall;

    private CensusTract tract;

    @Before
    public void setup() {
        this.tract = new CensusTract();
        this.tract.setGid("1");
        Authentication authentication = Mockito.mock(Authentication.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
        Mockito.when(service.getCensusTract(anyString())).thenReturn(tract);
        Mockito.when(service.getAllCensusTracts()).thenReturn(Arrays.asList(tract));
    }

    @Test
    public void getCensusTracts() throws Exception {
        mvc.perform(get("/census-tracts").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void getCensusTractById() throws Exception {
        mvc.perform(get("/census-tracts/{id}", this.tract.getGid()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getCensusTractMatchingTracts() throws Exception {
        mvc.perform(
                get("/census-tracts/{id}/matched-tracts", this.tract.getGid()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}