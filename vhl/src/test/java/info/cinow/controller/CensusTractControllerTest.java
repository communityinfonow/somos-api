package info.cinow.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import info.cinow.dto.mapper.CensusTractMapper;
import info.cinow.dto.mapper.CensusTractMapperImpl;
import info.cinow.model.CensusTract;
import info.cinow.service.CensusTractService;

/**
 * CensusTractControllerTest
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CensusTractController.class)
public class CensusTractControllerTest {

    @TestConfiguration
    static class CensusTractMapperContext {
        @Bean
        public CensusTractMapper tractmapper() {
            return new CensusTractMapperImpl();
        }
    }

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CensusTractService service;

    private CensusTract tract;

    @Before
    public void setup() {
        this.tract = new CensusTract();
        this.tract.setGid(1);
        Mockito.when(service.getCensusTract(anyInt())).thenReturn(tract);
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