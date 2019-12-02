package info.cinow.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import info.cinow.model.CensusTract;
import info.cinow.service.CensusTractService;

/**
 * CensusTractControllerTest
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CensusTractController.class)
public class CensusTractControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CensusTractService service;

    @Test
    public void canReturnGeometry() throws Exception {
        CensusTract tract = new CensusTract();
        tract.setPolygon(new GeometryFactory().createMultiPolygon(new Polygon[10]));
        when(service.getCensusTract(1)).thenReturn(tract);
        mvc.perform(get("/census-tracts/1")).andExpect(status().isOk());
    }

}