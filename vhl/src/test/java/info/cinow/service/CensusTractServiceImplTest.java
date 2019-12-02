package info.cinow.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import info.cinow.model.CensusTract;
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
        CensusTract tract = this.service.getCensusTract(1);
        // assertNotNull(tract.get)
    }

}