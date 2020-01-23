package info.cinow.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import info.cinow.dto.mapper.CensusTractMapper;
import info.cinow.model.CensusTract;

/**
 * CensusTractMapperTest
 */
@RunWith(SpringRunner.class)
public class CensusTractMapperTest {

    @Autowired
    private CensusTractMapper mapper;

    @MockBean
    private CensusTractMapper tractmapper;

    private CensusTract censusTract;

    @Before
    public void setup() {
        this.censusTract = new CensusTract();
        censusTract.setGid(1);
        censusTract.setTract("1234");
    }

    @Test
    public void toDtoWorks() {
        CensusTractDto dto = this.mapper.toDto(censusTract);
        assertEquals(dto.getId(), this.censusTract.getGid());
        assertEquals(dto.getTract(), this.censusTract.getTract());
        assertEquals(dto.getGeometry(), this.censusTract.getPolygon());

    }

}