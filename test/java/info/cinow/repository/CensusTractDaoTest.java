package info.cinow.repository;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import info.cinow.model.CensusTract;

/**
 * CensusTractDaoTest
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CensusTractDaoTest {

    @Autowired
    CensusTractDao realDao;

    @Test
    public void realDaoReturnsGeometry() {
        List<CensusTract> tract = new ArrayList<CensusTract>();
        this.realDao.findAll().forEach(tract::add);
        assertTrue(tract.size() > 0);
    }
}