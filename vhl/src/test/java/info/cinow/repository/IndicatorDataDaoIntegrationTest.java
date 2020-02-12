package info.cinow.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import info.cinow.model.Indicator;
import info.cinow.model.IndicatorData;

/**
 * CensusTractPhotoDaoIntegrationTest
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
public class IndicatorDataDaoIntegrationTest {

    @Autowired
    private IndicatorDataDao dao;

    private Indicator indicator;

    @Before
    public void setup() {
        this.indicator = new Indicator();
        this.indicator.setId(50001L);
    }

    @Test
    public void getALlData() {
        Set<IndicatorData> data = this.dao.findByIdIndicatorId(this.indicator.getId());
        assertFalse(data.isEmpty());
        data.forEach(obj -> {
            System.out.println("GID" + obj.getCensusTract().getGid());
        });
        // assertTrue(data.contains(photo));

    }
}