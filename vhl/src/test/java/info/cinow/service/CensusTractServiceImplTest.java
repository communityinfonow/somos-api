package info.cinow.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import info.cinow.dto.CensusTractDto;
import info.cinow.repository.CensusTractDao;

/**
 * CensusTractServiceImplTest
 */
@RunWith(SpringRunner.class)

public class CensusTractServiceImplTest {

    @MockBean
    CensusTractDao mockDao;

    @Autowired
    CensusTractDao realDao;

    @Autowired
    CensusTractService service;

    @Test
    public void realDaoReturnsGeometry() {
        CensusTractDto tract = this.service.getCensusTract(1);
        // assertNotNull(tract.get)
    }

}